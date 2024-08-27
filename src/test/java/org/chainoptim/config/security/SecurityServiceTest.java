package org.chainoptim.config.security;

import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.model.UserDetailsImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @Mock
    private CustomRoleSecurityService customRoleSecurityService;

    @InjectMocks
    private SecurityServiceImpl securityService;

    @Test
    void testCanAccessOrganizationEntity_AdminRole() {
        // Arrange
        setUserDetailsToContext(User.Role.ADMIN, null);

        // Act
        boolean canAccess = securityService.canAccessOrganizationEntity(Optional.of(1), "Product", "Read");

        // Assert
        assertTrue(canAccess);
    }

    @Test
    void testCanAccessOrganizationEntity_MemberRole() {
        // Arrange
        setUserDetailsToContext(User.Role.MEMBER, null);

        // Act
        boolean canAccessRead = securityService.canAccessOrganizationEntity(Optional.of(1), "Product", "Read");
        boolean canAccessCreate = securityService.canAccessOrganizationEntity(Optional.of(1), "Product", "Create");
        boolean canAccessUpdate = securityService.canAccessOrganizationEntity(Optional.of(1), "Product", "Update");
        boolean canAccessDelete = securityService.canAccessOrganizationEntity(Optional.of(1), "Product", "Delete");

        // Assert
        assertTrue(canAccessRead);
        assertFalse(canAccessCreate);
        assertFalse(canAccessUpdate);
        assertFalse(canAccessDelete);
    }

    @Test
    void testCanAccessOrganizationEntity_NoneRole() {
        // Arrange
        setUserDetailsToContext(User.Role.NONE, null);

        // Act
        boolean canAccess = securityService.canAccessOrganizationEntity(Optional.of(1), "Product", "Read");

        // Assert
        assertFalse(canAccess);
    }

    @Test
    void testCanAccessOrganizationEntity_CustomRole() {
        // Arrange
        setUserDetailsToContext(User.Role.NONE, new CustomRole());
        when(customRoleSecurityService.canUserAccessOrganizationEntity(any(), any(), any())).thenReturn(true);

        // Act
        boolean canAccess = securityService.canAccessOrganizationEntity(Optional.of(1), "Product", "Read");

        // Assert
        assertTrue(canAccess);
    }

    void setUserDetailsToContext(User.Role role, CustomRole customRole) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setOrganizationId(1);
        userDetails.setRole(role);
        userDetails.setCustomRole(customRole);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

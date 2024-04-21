package org.chainoptim.config.security;

import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.organization.model.FeaturePermissions;
import org.chainoptim.core.organization.model.Permissions;
import org.chainoptim.core.user.model.UserDetailsImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CustomRoleSecurityServiceTest {

    @InjectMocks
    private CustomRoleSecurityServiceImpl customRoleSecurityService;

    @Test
    void testCanUserAccessOrganizationEntity() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl();
        CustomRole customRole = new CustomRole();
        Permissions permissions = new Permissions();
        FeaturePermissions products = new FeaturePermissions(true, false, true, false);
        permissions.setProducts(products);
        customRole.setPermissions(permissions);
        userDetails.setCustomRole(customRole);

        // Act
        boolean canAccessProductRead = customRoleSecurityService.canUserAccessOrganizationEntity(1, userDetails, "Product", "Read");
        boolean canAccessProductCreate = customRoleSecurityService.canUserAccessOrganizationEntity(1, userDetails, "Product", "Create");
        boolean canAccessProductUpdate = customRoleSecurityService.canUserAccessOrganizationEntity(1, userDetails, "Product", "Update");
        boolean canAccessProductDelete = customRoleSecurityService.canUserAccessOrganizationEntity(1, userDetails, "Product", "Delete");
        boolean canAccessInvalidOperation = customRoleSecurityService.canUserAccessOrganizationEntity(1, userDetails, "Product", "InvalidOperation");

        // Assert
        assertTrue(canAccessProductRead);
        assertTrue(canAccessProductUpdate);
        assertFalse(canAccessProductCreate);
        assertFalse(canAccessProductDelete);
        assertFalse(canAccessInvalidOperation);
    }
}

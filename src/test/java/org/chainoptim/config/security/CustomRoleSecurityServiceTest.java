package org.chainoptim.config.security;

import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.organization.model.FeaturePermissions;
import org.chainoptim.core.organization.model.Permissions;
import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.shared.enums.Feature;

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
        boolean canAccessProductRead = customRoleSecurityService.canUserAccessOrganizationEntity(userDetails, Feature.PRODUCT, "Read");
        boolean canAccessProductCreate = customRoleSecurityService.canUserAccessOrganizationEntity(userDetails, Feature.PRODUCT, "Create");
        boolean canAccessProductUpdate = customRoleSecurityService.canUserAccessOrganizationEntity(userDetails, Feature.PRODUCT, "Update");
        boolean canAccessProductDelete = customRoleSecurityService.canUserAccessOrganizationEntity(userDetails, Feature.PRODUCT, "Delete");
        boolean canAccessInvalidOperation = customRoleSecurityService.canUserAccessOrganizationEntity(userDetails, Feature.PRODUCT, "InvalidOperation");

        // Assert
        assertTrue(canAccessProductRead);
        assertTrue(canAccessProductUpdate);
        assertFalse(canAccessProductCreate);
        assertFalse(canAccessProductDelete);
        assertFalse(canAccessInvalidOperation);
    }
}

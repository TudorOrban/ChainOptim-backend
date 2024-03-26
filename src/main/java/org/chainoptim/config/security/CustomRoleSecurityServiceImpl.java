package org.chainoptim.config.security;

import org.chainoptim.core.organization.model.FeaturePermissions;
import org.chainoptim.core.organization.model.Permissions;
import org.chainoptim.core.user.model.UserDetailsImpl;

import org.springframework.stereotype.Service;

@Service
public class CustomRoleSecurityServiceImpl implements CustomRoleSecurityService {

    public boolean canUserAccessOrganizationEntity(Integer organizationId, UserDetailsImpl userDetails, String entityType, String operationType) {
        Permissions userPermissions = userDetails.getCustomRole().getPermissions();
        if (userPermissions == null) {
            return false;
        }

        return switch (entityType) {
            case "Product" -> hasOperationAccess(userPermissions.getProducts(), operationType);
            case "Factory" -> hasOperationAccess(userPermissions.getFactories(), operationType);
            case "Warehouse" -> hasOperationAccess(userPermissions.getWarehouses(), operationType);
            case "Supplier" -> hasOperationAccess(userPermissions.getSuppliers(), operationType);
            case "Client" -> hasOperationAccess(userPermissions.getClients(), operationType);
            default -> true; // Allow access to entities not supported in custom permissions
        };
    }

    private boolean hasOperationAccess(FeaturePermissions permissions, String operationType) {
        if (permissions == null) {
            return false;
        }

        return switch (operationType) {
            case "Read" -> permissions.getCanRead();
            case "Create" -> permissions.getCanCreate();
            case "Update" -> permissions.getCanUpdate();
            case "Delete" -> permissions.getCanDelete();
            default -> false;
        };
    }
}

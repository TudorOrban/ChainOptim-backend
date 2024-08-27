package org.chainoptim.core.general.security.service;

import org.chainoptim.core.tenant.customrole.model.FeaturePermissions;
import org.chainoptim.core.tenant.customrole.model.Permissions;
import org.chainoptim.core.tenant.user.model.UserDetailsImpl;
import org.chainoptim.shared.enums.Feature;

import org.springframework.stereotype.Service;

@Service
public class CustomRoleSecurityServiceImpl implements CustomRoleSecurityService {

    public boolean canUserAccessOrganizationEntity(UserDetailsImpl userDetails, Feature feature, String operationType) {
        Permissions userPermissions = userDetails.getCustomRole().getPermissions();
        if (userPermissions == null || userPermissions.getFeaturePermissions() == null) {
            return false;
        }

        return hasOperationAccess(userPermissions.getFeaturePermissions().get(feature), operationType);
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

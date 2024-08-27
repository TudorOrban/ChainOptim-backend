package org.chainoptim.config.security;

import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.shared.enums.Feature;

public interface CustomRoleSecurityService {

    boolean canUserAccessOrganizationEntity(UserDetailsImpl userDetails, Feature feature, String operationType);
}

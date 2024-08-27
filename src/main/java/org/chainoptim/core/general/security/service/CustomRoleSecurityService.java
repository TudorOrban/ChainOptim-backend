package org.chainoptim.core.general.security.service;

import org.chainoptim.core.tenant.user.model.UserDetailsImpl;
import org.chainoptim.shared.enums.Feature;

public interface CustomRoleSecurityService {

    boolean canUserAccessOrganizationEntity(UserDetailsImpl userDetails, Feature feature, String operationType);
}

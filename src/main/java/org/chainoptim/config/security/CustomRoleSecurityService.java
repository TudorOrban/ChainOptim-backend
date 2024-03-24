package org.chainoptim.config.security;

import org.chainoptim.core.user.model.UserDetailsImpl;

public interface CustomRoleSecurityService {

    boolean canUserAccessOrganizationEntity(Integer organizationId, UserDetailsImpl userDetails, String entityType, String operationType);
}

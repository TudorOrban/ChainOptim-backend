package org.chainoptim.core.general.security.service;

import java.util.Optional;

public interface SecurityService {

    boolean canAccessEntity(Long entityId, String entityType, String operationType);
    boolean canAccessOrganizationEntity(Optional<Integer> organizationId, String entityType, String operationType);
    boolean canUserAccessOrganizationEntity(String userId, String operationType);
}

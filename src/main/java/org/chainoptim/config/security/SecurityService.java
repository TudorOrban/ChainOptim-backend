package org.chainoptim.config.security;

import java.util.Optional;

public interface SecurityService {

    boolean canAccessEntity(Long entityId, String entityType, String operationType);
    boolean canAccessOrganizationEntity(Optional<Integer> organizationId, String entityType, String operationType);
}

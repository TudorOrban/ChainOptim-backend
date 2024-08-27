package org.chainoptim.core.organization.service;

import java.util.Optional;

public interface OrganizationIdFinderService {

    Optional<Integer> findOrganizationIdByEntityId(Long entityId, String entityType);
}

package org.chainoptim.core.tenant.organization.service;

import org.chainoptim.shared.enums.Feature;

import java.util.Optional;

public interface OrganizationIdFinderService {

    Optional<Integer> findOrganizationIdByEntityId(Long entityId, Feature feature);
}

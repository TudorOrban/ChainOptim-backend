package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.Factory;

import java.util.List;

public interface FactoriesSearchRepository {
    List<Factory> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending);
}

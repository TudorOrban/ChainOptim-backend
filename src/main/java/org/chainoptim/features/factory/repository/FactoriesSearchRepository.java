package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface FactoriesSearchRepository {
    PaginatedResults<Factory> findByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

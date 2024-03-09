package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface FactoryInventorySearchRepository {

    PaginatedResults<FactoryInventoryItem> findFactoryItemsById(
            Integer factoryId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.Map;

public interface FactoryInventoryItemSearchRepository {

    PaginatedResults<FactoryInventoryItem> findFactoryItemsByIdAdvanced(
            Integer factoryId,
            String searchQuery, Map<String, String> filters,
            String sortBy, boolean ascending,
            int page, int itemsPerPage
    );
}

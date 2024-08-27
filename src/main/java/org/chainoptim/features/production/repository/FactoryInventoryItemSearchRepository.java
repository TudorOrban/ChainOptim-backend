package org.chainoptim.features.production.repository;

import org.chainoptim.features.production.model.FactoryInventoryItem;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface FactoryInventoryItemSearchRepository {

    PaginatedResults<FactoryInventoryItem> findByFactoryIdAdvanced(
            SearchMode searchMode,
            Integer factoryId,
            SearchParams searchParams
    );
}

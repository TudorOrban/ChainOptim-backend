package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.FactoryInventoryItem;
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

package org.chainoptim.features.storage.inventory.repository;

import org.chainoptim.features.storage.inventory.model.WarehouseInventoryItem;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface WarehouseInventoryItemSearchRepository {

    PaginatedResults<WarehouseInventoryItem> findByWarehouseIdAdvanced(
            SearchMode searchMode,
            Integer warehouseId,
            SearchParams searchParams
    );
}

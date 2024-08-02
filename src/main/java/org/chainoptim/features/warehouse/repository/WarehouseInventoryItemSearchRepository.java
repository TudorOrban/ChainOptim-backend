package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
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

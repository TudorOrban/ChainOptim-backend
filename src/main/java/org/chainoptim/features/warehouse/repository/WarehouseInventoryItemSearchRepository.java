package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.Map;

public interface WarehouseInventoryItemSearchRepository {

    PaginatedResults<WarehouseInventoryItem> findWarehouseItemsByIdAdvanced(
            Integer warehouseId,
            String searchQuery, Map<String, String> filters,
            String sortBy, boolean ascending,
            int page, int itemsPerPage
    );
}

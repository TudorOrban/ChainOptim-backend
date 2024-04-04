package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface WarehouseInventorySearchRepository {

    PaginatedResults<WarehouseInventoryItem> findWarehouseItemsById(
            Integer warehouseId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

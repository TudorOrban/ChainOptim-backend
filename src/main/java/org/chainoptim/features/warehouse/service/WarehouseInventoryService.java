package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface WarehouseInventoryService {

    // Fetch
    List<WarehouseInventoryItem> getWarehouseInventoryItemsByWarehouseId(Integer warehouseId);
    PaginatedResults<WarehouseInventoryItem> getWarehouseInventoryItemsByWarehouseIdAdvanced(
            Integer warehouseId,
            String searchQuery, String filtersJson,
            String sortBy, boolean ascending,
            int page, int itemsPerPage);
    WarehouseInventoryItem getWarehouseInventoryItemById(Integer itemId);

    // Create
    WarehouseInventoryItem createWarehouseInventoryItem(CreateWarehouseInventoryItemDTO itemDTO);
    List<WarehouseInventoryItem> createWarehouseInventoryItemsInBulk(List<CreateWarehouseInventoryItemDTO> itemDTOs);

    // Update
    WarehouseInventoryItem updateWarehouseInventoryItem(UpdateWarehouseInventoryItemDTO itemDTO);
    List<WarehouseInventoryItem> updateWarehouseInventoryItemsInBulk(List<UpdateWarehouseInventoryItemDTO> itemDTOs);

    // Delete
    void deleteWarehouseInventoryItem(Integer itemId);
}

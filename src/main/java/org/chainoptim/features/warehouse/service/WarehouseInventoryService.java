package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface WarehouseInventoryService {

    // Fetch
    List<WarehouseInventoryItem> getWarehouseInventoryItemsByWarehouseId(Integer warehouseId);
    PaginatedResults<WarehouseInventoryItem> getWarehouseInventoryItemsAdvanced(SearchMode searchMode, Integer entityId, SearchParams searchParams);
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

package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;

import java.util.List;

public interface WarehouseInventoryService {

    List<WarehouseInventoryItem> getWarehouseInventoryItemsByWarehouseId(Integer warehouseId);
}

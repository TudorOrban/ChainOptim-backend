package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseInventoryRepository {

    List<WarehouseInventoryItem> findByWarehouseId(Integer warehouseId);
}

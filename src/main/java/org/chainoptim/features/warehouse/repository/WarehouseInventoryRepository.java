package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventoryItem, Integer> {

    List<WarehouseInventoryItem> findByWarehouseId(Integer warehouseId);
}

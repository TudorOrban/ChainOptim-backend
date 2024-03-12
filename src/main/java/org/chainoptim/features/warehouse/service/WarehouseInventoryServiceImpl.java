package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.features.warehouse.repository.WarehouseInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseInventoryServiceImpl implements WarehouseInventoryService {

    private final WarehouseInventoryRepository warehouseInventoryRepository;

    @Autowired
    public WarehouseInventoryServiceImpl(WarehouseInventoryRepository warehouseInventoryRepository) {
        this.warehouseInventoryRepository = warehouseInventoryRepository;
    }

    public List<WarehouseInventoryItem> getWarehouseInventoryItemsByWarehouseId(Integer warehouseId) {
        return warehouseInventoryRepository.findByWarehouseId(warehouseId);
    }
}

package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> getWarehouseById(Integer id) {
        return warehouseRepository.findById(id);
    }

    public List<Warehouse> getWarehousesByOrganizationId(Integer organizationId) {
        return warehouseRepository.findByOrganizationId(organizationId);
    }
}

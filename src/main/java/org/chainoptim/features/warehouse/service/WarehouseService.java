package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    public List<Warehouse> getAllWarehouses();
    public Optional<Warehouse> getWarehouseById(Integer id);
    public List<Warehouse> getWarehousesByOrganizationId(Integer organizationId);
}

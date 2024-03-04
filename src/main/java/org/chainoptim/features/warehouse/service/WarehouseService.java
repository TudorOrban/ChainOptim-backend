package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.dto.WarehousesSearchDTO;
import org.chainoptim.features.warehouse.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    List<Warehouse> getAllWarehouses();
    Optional<Warehouse> getWarehouseById(Integer id);
    List<Warehouse> getWarehousesByOrganizationId(Integer organizationId);
    List<WarehousesSearchDTO> getWarehousesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending);
}

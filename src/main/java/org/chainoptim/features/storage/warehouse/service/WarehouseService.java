package org.chainoptim.features.storage.warehouse.service;

import org.chainoptim.features.storage.warehouse.dto.CreateWarehouseDTO;
import org.chainoptim.features.storage.warehouse.dto.UpdateWarehouseDTO;
import org.chainoptim.features.storage.warehouse.dto.WarehouseOverviewDTO;
import org.chainoptim.features.storage.warehouse.dto.WarehousesSearchDTO;
import org.chainoptim.features.storage.warehouse.model.Warehouse;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface WarehouseService {

    Warehouse getWarehouseById(Integer id);
    List<WarehousesSearchDTO> getWarehousesByOrganizationIdSmall(Integer organizationId);
    List<Warehouse> getWarehousesByOrganizationId(Integer organizationId);
    PaginatedResults<WarehousesSearchDTO> getWarehousesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    WarehouseOverviewDTO getWarehouseOverview(Integer warehouseId);

    // Create
    Warehouse createWarehouse(CreateWarehouseDTO warehouseDTO);

    // Update
    Warehouse updateWarehouse(UpdateWarehouseDTO updateWarehouseDTO);

    // Delete
    void deleteWarehouse(Integer warehouseId);
}

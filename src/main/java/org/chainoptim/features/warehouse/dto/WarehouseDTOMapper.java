package org.chainoptim.features.warehouse.dto;

import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.shared.commonfeatures.location.model.Location;

public class WarehouseDTOMapper {

    private WarehouseDTOMapper() {}

    public static WarehousesSearchDTO convertToWarehousesSearchDTO(Warehouse warehouse) {
        WarehousesSearchDTO dto = new WarehousesSearchDTO();
        dto.setId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setCreatedAt(warehouse.getCreatedAt());
        dto.setUpdatedAt(warehouse.getUpdatedAt());
        dto.setLocation(warehouse.getLocation());
        return dto;
    }

    public static Warehouse convertCreateWarehouseDTOToWarehouse(CreateWarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(warehouseDTO.getName());
        warehouse.setOrganizationId(warehouseDTO.getOrganizationId());
        Location location = new Location();
        location.setId(warehouseDTO.getLocationId());
        warehouse.setLocation(location);

        return warehouse;
    }
}

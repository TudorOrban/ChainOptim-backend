package org.chainoptim.features.warehouse.dto;

import org.chainoptim.features.warehouse.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.model.Stage;
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
        if (warehouseDTO.getLocationId() != null) {
            Location location = new Location();
            location.setId(warehouseDTO.getLocationId());
            warehouse.setLocation(location);
        }

        return warehouse;
    }

    public static WarehouseInventoryItem convertCreateWarehouseItemDTOToWarehouseItem(CreateWarehouseInventoryItemDTO itemDTO) {
        WarehouseInventoryItem item = new WarehouseInventoryItem();
        item.setWarehouseId(itemDTO.getWarehouseId());
        if (itemDTO.getComponentId() != null) {
            Component component = new Component();
            component.setId(itemDTO.getComponentId());
            item.setComponent(component);
        }
        if (itemDTO.getProductId() != null) {
            Product product = new Product();
            product.setId(itemDTO.getProductId());
            item.setProduct(product);
        }
        item.setQuantity(itemDTO.getQuantity());
        item.setMinimumRequiredQuantity(itemDTO.getMinimumRequiredQuantity());

        return item;
    }
}

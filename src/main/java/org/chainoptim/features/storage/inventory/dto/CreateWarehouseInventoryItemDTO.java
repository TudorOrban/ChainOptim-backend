package org.chainoptim.features.storage.inventory.dto;

import lombok.Data;

@Data
public class CreateWarehouseInventoryItemDTO {

    private Integer warehouseId;
    private Integer organizationId;
    private Integer productId;
    private Integer componentId;
    private Float quantity;
    private Float minimumRequiredQuantity;
}

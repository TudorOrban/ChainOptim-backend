package org.chainoptim.features.storage.dto;

import lombok.Data;

@Data
public class UpdateWarehouseInventoryItemDTO {

    private Integer id;
    private Integer warehouseId;
    private Integer organizationId;
    private Integer productId;
    private Integer componentId;
    private Float quantity;
    private Float minimumRequiredQuantity;
}

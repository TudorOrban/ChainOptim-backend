package org.chainoptim.features.production.inventory.dto;

import lombok.Data;

@Data
public class UpdateFactoryInventoryItemDTO {

    private Integer id;
    private Integer factoryId;
    private Integer organizationId;
    private Integer productId;
    private Integer componentId;
    private Float quantity;
    private Float minimumRequiredQuantity;
}

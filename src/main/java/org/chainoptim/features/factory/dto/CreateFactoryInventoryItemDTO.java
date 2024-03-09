package org.chainoptim.features.factory.dto;

import lombok.Data;

@Data
public class CreateFactoryInventoryItemDTO {

    private Integer factoryId;
    private Integer productId;
    private Integer componentId;
    private Float quantity;
    private Float minimumRequiredQuantity;
}

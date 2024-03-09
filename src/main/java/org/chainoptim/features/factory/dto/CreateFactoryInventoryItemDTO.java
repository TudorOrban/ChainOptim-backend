package org.chainoptim.features.factory.dto;

import lombok.Data;

@Data
public class CreateFactoryInventoryItemDTO {

    private Integer factoryId;
    private Integer productId;
    private Integer componentId;
    private Double quantity;
    private Double minimumRequiredQuantity;
}

package org.chainoptim.features.factory.dto;

import lombok.Data;

@Data
public class UpdateFactoryInventoryItemDTO {

    private Integer id;
    private Integer factoryId;
    private Integer productId;
    private Integer componentId;
    private Float quantity;
    private Float minimumRequiredQuantity;
}

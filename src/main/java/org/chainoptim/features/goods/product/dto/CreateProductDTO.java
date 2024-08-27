package org.chainoptim.features.goods.product.dto;

import org.chainoptim.features.goods.unit.model.UnitOfMeasurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {

    private String name;
    private String description;
    private Integer organizationId;
    private UnitOfMeasurement newUnit;
}

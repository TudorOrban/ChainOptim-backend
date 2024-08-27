package org.chainoptim.features.goods.product.dto;

import org.chainoptim.features.goods.unit.model.UnitOfMeasurement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductDTO {

    private Integer id;
    private String name;
    private String description;
    private UnitOfMeasurement newUnit;
}

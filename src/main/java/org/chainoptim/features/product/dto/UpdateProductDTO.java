package org.chainoptim.features.product.dto;

import org.chainoptim.features.product.model.NewUnitOfMeasurement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer unitId;
    private NewUnitOfMeasurement newUnit;
}

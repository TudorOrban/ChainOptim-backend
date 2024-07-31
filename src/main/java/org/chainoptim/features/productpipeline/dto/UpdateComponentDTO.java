package org.chainoptim.features.productpipeline.dto;

import org.chainoptim.features.product.model.NewUnitOfMeasurement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateComponentDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer unitId;
    private NewUnitOfMeasurement newUnit;
}

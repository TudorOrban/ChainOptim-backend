package org.chainoptim.features.goods.product.dto;

import org.chainoptim.features.goods.controller.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.goods.unit.model.NewUnitOfMeasurement;
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
    private Integer unitId;
    private CreateUnitOfMeasurementDTO unitDTO;
    private boolean createUnit;
    private NewUnitOfMeasurement newUnit;
}

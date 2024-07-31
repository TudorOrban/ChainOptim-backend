package org.chainoptim.features.productpipeline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.features.product.dto.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.product.model.NewUnitOfMeasurement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateComponentDTO {

    private String name;
    private String description;
    private Integer organizationId;
    private Integer unitId;
    private CreateUnitOfMeasurementDTO unitDTO;
    private boolean createUnit;
    private NewUnitOfMeasurement newUnit;
}

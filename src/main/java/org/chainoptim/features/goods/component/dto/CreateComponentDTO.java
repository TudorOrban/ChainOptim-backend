package org.chainoptim.features.goods.component.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.features.goods.unit.model.UnitOfMeasurement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateComponentDTO {

    private String name;
    private String description;
    private Integer organizationId;
    private UnitOfMeasurement newUnit;
}

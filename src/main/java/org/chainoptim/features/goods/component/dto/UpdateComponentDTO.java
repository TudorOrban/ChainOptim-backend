package org.chainoptim.features.goods.component.dto;

import org.chainoptim.features.goods.unit.model.UnitOfMeasurement;
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
    private UnitOfMeasurement newUnit;
}

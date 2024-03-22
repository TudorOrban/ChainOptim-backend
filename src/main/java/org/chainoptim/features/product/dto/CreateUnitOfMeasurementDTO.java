package org.chainoptim.features.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUnitOfMeasurementDTO {

    private String name;
//    private String abbreviation;

    private String unitType;
    private Integer organizationId;
}

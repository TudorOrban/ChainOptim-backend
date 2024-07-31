package org.chainoptim.features.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUnitOfMeasurement {

    StandardUnit standardUnit;
    UnitMagnitude unitMagnitude;

    public String getFullName() {
        return standardUnit.getName() + unitMagnitude.getName();
    }

    public String getAbbreviation() {
        return standardUnit.getAbbreviation() + unitMagnitude.getAbbreviation();
    }
}

package org.chainoptim.features.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUnitOfMeasurement {

    StandardUnit standardUnit;
    UnitMagnitude unitMagnitude;

    @JsonIgnore
    public String getFullName() {
        return unitMagnitude.getName() + standardUnit.getName();
    }

    @JsonIgnore
    public String getAbbreviation() {
        return unitMagnitude.getAbbreviation() + standardUnit.getAbbreviation();
    }
}

package org.chainoptim.features.product.dto;

import org.chainoptim.features.product.model.UnitOfMeasurement;

import static org.chainoptim.shared.util.ReflectionUtil.setPropertyWithNullCheck;

public class UnitDTOMapper {

    private UnitDTOMapper() {}

    public static UnitOfMeasurement convertCreateUnitDTOToUnit(CreateUnitOfMeasurementDTO unitDTO) {
        UnitOfMeasurement unit = new UnitOfMeasurement();
        unit.setName(unitDTO.getName());
        unit.setUnitType(unitDTO.getUnitType());
        unit.setOrganizationId(unitDTO.getOrganizationId());

        return unit;
    }

    public static UnitOfMeasurement updateUnitFromUpdateUnitDTO(UnitOfMeasurement unit, UpdateUnitOfMeasurementDTO unitDTO) {
        setPropertyWithNullCheck(unit, "name", unitDTO.getName());
        setPropertyWithNullCheck(unit, "unitType", unitDTO.getUnitType());

        return unit;
    }
}

package org.chainoptim.features.goods.controller;

import org.chainoptim.features.goods.controller.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.goods.controller.UpdateUnitOfMeasurementDTO;
import org.chainoptim.features.goods.controller.UnitOfMeasurement;

import java.util.List;

public interface UnitOfMeasurementService {

    List<UnitOfMeasurement> getUnitsOfMeasurementByOrganizationId(Integer organizationId);
    UnitOfMeasurement createUnitOfMeasurement(CreateUnitOfMeasurementDTO unitDTO);
    UnitOfMeasurement updateUnitOfMeasurement(UpdateUnitOfMeasurementDTO unitDTO);
    void deleteUnitOfMeasurement(Integer unitId);
}

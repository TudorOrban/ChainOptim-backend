package org.chainoptim.features.product.service;

import org.chainoptim.features.product.dto.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.product.dto.UpdateUnitOfMeasurementDTO;
import org.chainoptim.features.product.model.UnitOfMeasurement;

import java.util.List;

public interface UnitOfMeasurementService {

    List<UnitOfMeasurement> getUnitsOfMeasurementByOrganizationId(Integer organizationId);
    UnitOfMeasurement createUnitOfMeasurement(CreateUnitOfMeasurementDTO unitDTO);
    UnitOfMeasurement updateUnitOfMeasurement(UpdateUnitOfMeasurementDTO unitDTO);
    void deleteUnitOfMeasurement(Integer unitId);
}

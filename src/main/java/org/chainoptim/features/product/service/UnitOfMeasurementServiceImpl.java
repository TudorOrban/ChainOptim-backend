package org.chainoptim.features.product.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.dto.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.product.dto.UnitDTOMapper;
import org.chainoptim.features.product.dto.UpdateUnitOfMeasurementDTO;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.product.repository.UnitOfMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService {

    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    @Autowired
    public UnitOfMeasurementServiceImpl(UnitOfMeasurementRepository unitOfMeasurementRepository) {
        this.unitOfMeasurementRepository = unitOfMeasurementRepository;
    }

    // Fetch
    public List<UnitOfMeasurement> getUnitsOfMeasurementByOrganizationId(Integer organizationId) {
        return unitOfMeasurementRepository.findUnitsOfMeasurementByOrganizationId(organizationId);
    }

    // Create
    public UnitOfMeasurement createUnitOfMeasurement(CreateUnitOfMeasurementDTO unitDTO) {
        return unitOfMeasurementRepository.save(UnitDTOMapper.convertCreateUnitDTOToUnit(unitDTO));
    }

    // Update
    public UnitOfMeasurement updateUnitOfMeasurement(UpdateUnitOfMeasurementDTO unitDTO) {
        UnitOfMeasurement unit = unitOfMeasurementRepository.findById(unitDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit of measurement with ID: " + unitDTO.getId() + " not found."));
        UnitOfMeasurement unit1 = UnitDTOMapper.updateUnitFromUpdateUnitDTO(unit, unitDTO);
        return unitOfMeasurementRepository.save(unit1);
    }

    // Delete
    public void deleteUnitOfMeasurement(Integer unitId) {
        unitOfMeasurementRepository.deleteById(unitId);
    }
}

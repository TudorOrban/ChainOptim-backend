package org.chainoptim.features.goods.controller;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService {

    private final UnitOfMeasurementRepository unitOfMeasurementRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public UnitOfMeasurementServiceImpl(UnitOfMeasurementRepository unitOfMeasurementRepository,
                                        EntitySanitizerService sanitizerService) {
        this.unitOfMeasurementRepository = unitOfMeasurementRepository;
        this.sanitizerService = sanitizerService;
    }

    // Fetch
    public List<UnitOfMeasurement> getUnitsOfMeasurementByOrganizationId(Integer organizationId) {
        return unitOfMeasurementRepository.findUnitsOfMeasurementByOrganizationId(organizationId);
    }

    // Create
    public UnitOfMeasurement createUnitOfMeasurement(CreateUnitOfMeasurementDTO unitDTO) {
        CreateUnitOfMeasurementDTO sanitizedDTO = sanitizerService.sanitizeCreateUnitOfMeasurementDTO(unitDTO);
        return unitOfMeasurementRepository.save(UnitDTOMapper.convertCreateUnitDTOToUnit(sanitizedDTO));
    }

    // Update
    public UnitOfMeasurement updateUnitOfMeasurement(UpdateUnitOfMeasurementDTO unitDTO) {
        UpdateUnitOfMeasurementDTO sanitizedDTO = sanitizerService.sanitizeUpdateUnitOfMeasurementDTO(unitDTO);
        UnitOfMeasurement unit = unitOfMeasurementRepository.findById(sanitizedDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit of measurement with ID: " + sanitizedDTO.getId() + " not found."));
        UnitOfMeasurement unit1 = UnitDTOMapper.updateUnitFromUpdateUnitDTO(unit, sanitizedDTO);
        return unitOfMeasurementRepository.save(unit1);
    }

    // Delete
    public void deleteUnitOfMeasurement(Integer unitId) {
        unitOfMeasurementRepository.deleteById(unitId);
    }
}

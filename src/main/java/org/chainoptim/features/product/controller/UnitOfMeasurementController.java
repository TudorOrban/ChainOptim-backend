package org.chainoptim.features.product.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.product.dto.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.product.dto.UpdateUnitOfMeasurementDTO;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.product.service.UnitOfMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/units-of-measurement")
public class UnitOfMeasurementController {

    private final UnitOfMeasurementService unitOfMeasurementService;
    private final SecurityService securityService;

    @Autowired
    public UnitOfMeasurementController(UnitOfMeasurementService unitOfMeasurementService, SecurityService securityService) {
        this.unitOfMeasurementService = unitOfMeasurementService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"UnitOfMeasurement\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<UnitOfMeasurement>> getUnitsOfMeasurementByOrganizationId(@PathVariable("organizationId") Integer organizationId) {
        List<UnitOfMeasurement> units = unitOfMeasurementService.getUnitsOfMeasurementByOrganizationId(organizationId);
        return ResponseEntity.ok(units);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#unitDTO.getOrganizationId(), \"UnitOfMeasurement\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<UnitOfMeasurement> createUnitOfMeasurement(@RequestBody CreateUnitOfMeasurementDTO unitDTO) {
        UnitOfMeasurement newUnit = unitOfMeasurementService.createUnitOfMeasurement(unitDTO);
        return ResponseEntity.ok(newUnit);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#unitDTO.getId(), \"UnitOfMeasurement\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<UnitOfMeasurement> updateUnitOfMeasurement(@RequestBody UpdateUnitOfMeasurementDTO unitDTO) {
        UnitOfMeasurement updatedUnit = unitOfMeasurementService.updateUnitOfMeasurement(unitDTO);
        return ResponseEntity.ok(updatedUnit);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#unitId, \"UnitOfMeasurement\", \"Delete\")")
    @DeleteMapping("/delete/{unitId}")
    public ResponseEntity<Void> deleteUnitOfMeasurement(@PathVariable("unitId") Integer unitId) {
        unitOfMeasurementService.deleteUnitOfMeasurement(unitId);
        return ResponseEntity.ok().build();
    }
}

package org.chainoptim.features.warehouse.controller;

import org.chainoptim.features.warehouse.dto.CreateCompartmentDTO;
import org.chainoptim.features.warehouse.dto.UpdateCompartmentDTO;
import org.chainoptim.features.warehouse.model.Compartment;
import org.chainoptim.features.warehouse.service.CompartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/compartments")
public class CompartmentController {
    
    private final CompartmentService compartmentService;
    
    @Autowired
    public CompartmentController(CompartmentService compartmentService) {
        this.compartmentService = compartmentService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Compartment\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Compartment>> getCompartmentsByOrganizationId(@PathVariable Integer organizationId) {
        List<Compartment> compartments = compartmentService.getCompartmentsByOrganizationId(organizationId);
        if (compartments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(compartments);
    }

    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\", \"Read\")")
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Compartment>> getCompartmentsByWarehouseId(@PathVariable Integer warehouseId) {
        List<Compartment> compartments = compartmentService.getCompartmentsByWarehouseId(warehouseId);
        return ResponseEntity.ok(compartments);
    }

    @PreAuthorize("@securityService.canAccessEntity(#compartmentId, \"Compartment\", \"Read\")")
    @GetMapping("/{compartmentId}")
    public ResponseEntity<Compartment> getCompartmentById(@PathVariable Integer compartmentId) {
        Compartment compartment = compartmentService.getCompartmentById(compartmentId);
        return ResponseEntity.ok(compartment);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#compartmentDTO.getOrganizationId(), \"Compartment\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Compartment> createCompartment(@RequestBody CreateCompartmentDTO compartmentDTO) {
        Compartment compartment = compartmentService.createCompartment(compartmentDTO);
        return ResponseEntity.ok(compartment);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#compartmentDTO.getId(), \"Compartment\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Compartment> updateCompartment(@RequestBody UpdateCompartmentDTO compartmentDTO) {
        Compartment compartment = compartmentService.updateCompartment(compartmentDTO);
        return ResponseEntity.ok(compartment);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#compartmentId, \"Compartment\", \"Delete\")")
    @DeleteMapping("/delete/{compartmentId}")
    public ResponseEntity<Void> deleteCompartment(@PathVariable Integer compartmentId) {
        compartmentService.deleteCompartment(compartmentId);
        return ResponseEntity.ok().build();
    }
}

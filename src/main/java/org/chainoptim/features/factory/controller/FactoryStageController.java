package org.chainoptim.features.factory.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.factory.dto.CreateFactoryStageDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryStageDTO;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.factory.service.FactoryStageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/factory-stages")
public class FactoryStageController {

    private final FactoryStageService factoryStageService;
    private final SecurityService securityService;

    @Autowired
    public FactoryStageController(FactoryStageService factoryStageService,
                                  SecurityService securityService) {
        this.factoryStageService = factoryStageService;
        this.securityService = securityService;
    }

    // Fetch
    // TODO: Secure endpoint
    @GetMapping("/{factoryStageId}")
    public ResponseEntity<FactoryStage> getFactoryStageById(@PathVariable Integer factoryStageId) {
        FactoryStage stage = factoryStageService.getFactoryStageById(factoryStageId);
        return ResponseEntity.ok(stage);
    }

    // Create
    // TODO: Secure endpoint
//    @PreAuthorize("@securityService.canAccessEntity(#stageDTO.getFactoryId(), 'Factory', 'Create')")
    @PostMapping("/create/{refreshGraph}")
    public ResponseEntity<FactoryStage> createFactoryStage(@RequestBody CreateFactoryStageDTO stageDTO, @PathVariable("refreshGraph") Boolean refreshGraph) {
        FactoryStage stage = factoryStageService.createFactoryStage(stageDTO, refreshGraph);
        return ResponseEntity.ok(stage);
    }

    // Update
    // TODO: Secure endpoint
//    @PreAuthorize("@securityService.canAccessEntity(#stageDTO.getFactoryId(), 'Factory', 'Update')")
    @PutMapping("/update")
    public ResponseEntity<FactoryStage> updateFactoryStage(@RequestBody UpdateFactoryStageDTO factoryDTO) {
        FactoryStage stage = factoryStageService.updateFactoryStage(factoryDTO);
        return ResponseEntity.ok(stage);
    }

    // Delete
    // TODO: Secure endpoint
    @DeleteMapping("/delete/{factoryStageId}")
    public ResponseEntity<String> deleteFactoryStage(@PathVariable Integer factoryStageId) {
        factoryStageService.deleteFactoryStage(factoryStageId);
        return ResponseEntity.ok("Factory Stage with ID: " + factoryStageId + " deleted successfully.");
    }
}

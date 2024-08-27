package org.chainoptim.features.production.controller;

import org.chainoptim.core.general.security.service.SecurityService;
import org.chainoptim.features.production.dto.CreateFactoryStageDTO;
import org.chainoptim.features.production.dto.FactoryStageSearchDTO;
import org.chainoptim.features.production.dto.UpdateFactoryStageDTO;
import org.chainoptim.features.production.model.FactoryStage;
import org.chainoptim.features.production.service.FactoryStageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factory-stages")
public class FactoryStageController { // TODO: Secure endpoints

    private final FactoryStageService factoryStageService;
    private final SecurityService securityService;

    @Autowired
    public FactoryStageController(FactoryStageService factoryStageService,
                                  SecurityService securityService) {
        this.factoryStageService = factoryStageService;
        this.securityService = securityService;
    }

    // Fetch
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<FactoryStageSearchDTO>> getFactoryStagesByOrganizationId(@PathVariable Integer organizationId) {
        List<FactoryStageSearchDTO> stages = factoryStageService.getFactoryStagesByOrganizationId(organizationId);
        return ResponseEntity.ok(stages);
    }

    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<List<FactoryStageSearchDTO>> getFactoryStageByFactoryId(@PathVariable Integer factoryId) {
        List<FactoryStageSearchDTO> stages = factoryStageService.getFactoryStagesByFactoryId(factoryId);
        return ResponseEntity.ok(stages);
    }

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
    @PutMapping("/update/{refreshGraph}")
    public ResponseEntity<FactoryStage> updateFactoryStage(@RequestBody UpdateFactoryStageDTO factoryDTO, @PathVariable("refreshGraph") Boolean refreshGraph) {
        FactoryStage stage = factoryStageService.updateFactoryStage(factoryDTO, refreshGraph);
        return ResponseEntity.ok(stage);
    }

    // Delete
    // TODO: Secure endpoint
    @DeleteMapping("/delete/{factoryStageId}/{refreshGraph}")
    public ResponseEntity<Void> deleteFactoryStage(@PathVariable Integer factoryStageId, @PathVariable("refreshGraph") Boolean refreshGraph) {
        factoryStageService.deleteFactoryStage(factoryStageId, refreshGraph);
        return ResponseEntity.ok().build();
    }
}

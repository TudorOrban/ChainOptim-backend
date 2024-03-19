package org.chainoptim.features.factory.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.factory.dto.CreateFactoryStageDTO;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.factory.service.FactoryStageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // Create
    @PreAuthorize("@securityService.canAccessEntity(#stageDTO.getFactoryId(), 'Factory')")
    @PostMapping("/create")
    public ResponseEntity<FactoryStage> createFactoryStage(@RequestBody CreateFactoryStageDTO stageDTO) {
        FactoryStage stage = factoryStageService.createFactoryStage(stageDTO);
        return ResponseEntity.ok(stage);
    }
}

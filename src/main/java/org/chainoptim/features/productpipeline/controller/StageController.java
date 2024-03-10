package org.chainoptim.features.productpipeline.controller;

import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stages")
public class StageController {

    private final StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping("/{stageId}")
    public ResponseEntity<Stage> getStageById(@PathVariable("stageId") Integer stageId) {
        Stage stage = stageService.getStageById(stageId);
        return ResponseEntity.ok(stage);
    }
}

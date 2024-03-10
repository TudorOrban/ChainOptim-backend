package org.chainoptim.features.factory.controller;

import org.chainoptim.features.factory.model.FactoryEvaluationReport;
import org.chainoptim.features.factory.service.FactoryEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/factories/evaluation")
public class FactoryEvaluationController {

    private final FactoryEvaluationService factoryEvaluationService;

    @Autowired
    public FactoryEvaluationController(FactoryEvaluationService factoryEvaluationService) {
        this.factoryEvaluationService = factoryEvaluationService;
    }

    @GetMapping("/{factoryId}")
    public ResponseEntity<FactoryEvaluationReport> evaluateFactory(@PathVariable("factoryId") Integer factoryId) {
        return ResponseEntity.ok(factoryEvaluationService.evaluateFactory(factoryId));
    }
}

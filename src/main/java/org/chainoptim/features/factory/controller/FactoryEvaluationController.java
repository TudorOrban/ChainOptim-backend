package org.chainoptim.features.factory.controller;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.repository.AllocationPlan;
import org.chainoptim.features.evaluation.production.services.FactoryGraphEvaluationService;
import org.chainoptim.features.factory.model.FactoryEvaluationReport;
import org.chainoptim.features.factory.service.FactoryEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factories/evaluation")
public class FactoryEvaluationController {

    private final FactoryEvaluationService factoryEvaluationService;
    private final FactoryGraphEvaluationService factoryGraphEvaluationService;

    @Autowired
    public FactoryEvaluationController(
            FactoryEvaluationService factoryEvaluationService,
            FactoryGraphEvaluationService factoryGraphEvaluationService
   ) {
        this.factoryEvaluationService = factoryEvaluationService;
        this.factoryGraphEvaluationService = factoryGraphEvaluationService;
    }

    @GetMapping("/{factoryId}")
    public ResponseEntity<FactoryEvaluationReport> evaluateFactory(@PathVariable("factoryId") Integer factoryId) {
        return ResponseEntity.ok(factoryEvaluationService.evaluateFactory(factoryId));
    }

    @GetMapping("/graph/{factoryId}")
    public ResponseEntity<FactoryGraph> getFactoryGraph(@PathVariable("factoryId") Integer factoryId) {
        return ResponseEntity.ok(factoryEvaluationService.getFactoryPipelineGraph(factoryId));
    }

    @GetMapping("/graph/pipelines/{factoryId}")
    public ResponseEntity<AllocationPlan> getFactoryPipelines(@PathVariable("factoryId") Integer factoryId) {
        return ResponseEntity.ok(factoryGraphEvaluationService.getFactoryPipelines(factoryId, 10.0f));
    }

}

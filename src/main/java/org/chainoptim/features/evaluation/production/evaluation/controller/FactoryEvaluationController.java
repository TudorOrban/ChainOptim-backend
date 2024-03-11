package org.chainoptim.features.evaluation.production.evaluation.controller;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.evaluation.production.evaluation.service.FactoryGraphEvaluationService;
import org.chainoptim.features.evaluation.production.recommendation.model.FactoryRecommendationReport;
import org.chainoptim.features.evaluation.production.recommendation.service.FactoryRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/factories/evaluation")
public class FactoryEvaluationController {

    private final FactoryRecommendationService factoryRecommendationService;
    private final FactoryGraphEvaluationService factoryGraphEvaluationService;

    @Autowired
    public FactoryEvaluationController(
            FactoryRecommendationService factoryRecommendationService,
            FactoryGraphEvaluationService factoryGraphEvaluationService
   ) {
        this.factoryRecommendationService = factoryRecommendationService;
        this.factoryGraphEvaluationService = factoryGraphEvaluationService;
    }

    @GetMapping("/recommendations/{factoryId}")
    public ResponseEntity<FactoryRecommendationReport> makeRecommendationsToFactory(@PathVariable("factoryId") Integer factoryId) {
        return ResponseEntity.ok(factoryRecommendationService.evaluateFactory(factoryId));
    }

    @GetMapping("/graph/{factoryId}")
    public ResponseEntity<FactoryGraph> getFactoryGraph(@PathVariable("factoryId") Integer factoryId) {
        return ResponseEntity.ok(factoryRecommendationService.getFactoryPipelineGraph(factoryId));
    }

    @GetMapping("/graph/pipelines/{factoryId}")
    public ResponseEntity<AllocationPlan> evaluateFactory(@PathVariable("factoryId") Integer factoryId) {
        return ResponseEntity.ok(factoryGraphEvaluationService.evaluateFactory(factoryId, 10.0f));
    }

}

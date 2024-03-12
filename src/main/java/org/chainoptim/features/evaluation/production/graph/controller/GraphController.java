package org.chainoptim.features.evaluation.production.graph.controller;

import org.chainoptim.features.evaluation.production.graph.service.FactoryPipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/graphs")
public class GraphController {

    private final FactoryPipelineService factoryPipelineService;

    @Autowired
    public GraphController(FactoryPipelineService factoryPipelineService) {
        this.factoryPipelineService = factoryPipelineService;
    }

//    @GetMapping("/{factoryId}")
//    public ResponseEntity<List<FactoryGraph>> getIndependentPipelines(@PathVariable("factoryId") Integer factoryId) {
//        return graphService.analyzeGraph(factoryId)
//    }
}

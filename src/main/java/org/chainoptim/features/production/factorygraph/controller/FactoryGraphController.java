package org.chainoptim.features.production.factorygraph.controller;

import org.chainoptim.features.production.factorygraph.model.FactoryProductionGraph;
import org.chainoptim.features.production.factorygraph.service.FactoryPipelineService;
import org.chainoptim.features.production.factorygraph.service.FactoryProductionGraphService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factory-graphs")
public class FactoryGraphController {

    private final FactoryPipelineService factoryPipelineService;
    private final FactoryProductionGraphService graphService;

    @Autowired
    public FactoryGraphController(FactoryPipelineService factoryPipelineService, FactoryProductionGraphService graphService) {
        this.factoryPipelineService = factoryPipelineService;
        this.graphService = graphService;
    }

    // TODO: Secure endpoint
    @GetMapping("/{factoryId}")
    public ResponseEntity<List<FactoryProductionGraph>> getFactoryGraph(@PathVariable("factoryId") Integer factoryId) {
        List<FactoryProductionGraph> graphs = graphService.getProductionGraphByFactoryId(factoryId);
        return ResponseEntity.ok(graphs);
    }

    @PostMapping("/create/{factoryId}")
    public ResponseEntity<FactoryProductionGraph> createFactoryGraph(@PathVariable("factoryId") Integer factoryId) {
        FactoryProductionGraph newGraph = graphService.generateFactoryGraph(factoryId);
        return ResponseEntity.ok(newGraph);
    }

    // TODO: Secure endpoint
    @GetMapping("/update/{factoryId}/refresh")
    public ResponseEntity<FactoryProductionGraph> updateFactoryGraph(@PathVariable("factoryId") Integer factoryId) {
        FactoryProductionGraph graph = graphService.updateFactoryGraph(factoryId);
        return ResponseEntity.ok(graph);
    }

//    @GetMapping("/{factoryId}")
//    public ResponseEntity<List<FactoryGraph>> getIndependentPipelines(@PathVariable("factoryId") Integer factoryId) {
//        return graphService.analyzeGraph(factoryId)
//    }
}

package org.chainoptim.features.scanalysis.production.graph.controller;

import org.chainoptim.features.scanalysis.production.graph.model.FactoryProductionGraph;
import org.chainoptim.features.scanalysis.production.graph.service.FactoryPipelineService;
import org.chainoptim.features.scanalysis.production.graph.service.FactoryProductionGraphService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/graphs")
public class GraphController {

    private final FactoryPipelineService factoryPipelineService;
    private final FactoryProductionGraphService graphService;
    private static final Logger logger = LoggerFactory.getLogger(GraphController.class);

    @Autowired
    public GraphController(FactoryPipelineService factoryPipelineService, FactoryProductionGraphService graphService) {
        this.factoryPipelineService = factoryPipelineService;
        this.graphService = graphService;
    }

    @GetMapping("/{factoryId}")
    public ResponseEntity<List<FactoryProductionGraph>> getFactoryGraph(@PathVariable("factoryId") Integer factoryId) {
        List<FactoryProductionGraph> graphs = graphService.getProductionGraphByFactoryId(factoryId);
        logger.info("Graph: {}", graphs);
        return ResponseEntity.ok(graphs);
    }

//    @GetMapping("/{factoryId}")
//    public ResponseEntity<List<FactoryGraph>> getIndependentPipelines(@PathVariable("factoryId") Integer factoryId) {
//        return graphService.analyzeGraph(factoryId)
//    }
}

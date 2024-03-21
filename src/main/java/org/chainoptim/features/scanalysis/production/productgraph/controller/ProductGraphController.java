package org.chainoptim.features.scanalysis.production.productgraph.controller;

import org.chainoptim.features.scanalysis.production.productgraph.model.ProductProductionGraph;
import org.chainoptim.features.scanalysis.production.productgraph.service.ProductProductionGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-graphs")
public class ProductGraphController {

//    private final ProductPipelineService productPipelineService;
    private final ProductProductionGraphService graphService;

    @Autowired
    public ProductGraphController(ProductProductionGraphService graphService) {
        this.graphService = graphService;
    }

    // TODO: Secure endpoint
    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductProductionGraph>> getProductGraph(@PathVariable("productId") Integer productId) {
        List<ProductProductionGraph> graphs = graphService.getProductionGraphByProductId(productId);
        return ResponseEntity.ok(graphs);
    }

    // Create
    @PostMapping("/create/{productId}")
    public ResponseEntity<ProductProductionGraph> createProductGraph(@PathVariable("productId") Integer productId) {
        ProductProductionGraph newGraph = graphService.generateProductGraph(productId);
        return ResponseEntity.ok(newGraph);
    }

    // TODO: Secure endpoint
    @PutMapping("/update/{productId}/refresh")
    public ResponseEntity<ProductProductionGraph> updateProductGraph(@PathVariable("productId") Integer productId) {
        ProductProductionGraph graph = graphService.updateProductGraph(productId);
        return ResponseEntity.ok(graph);
    }

//    @GetMapping("/{productId}")
//    public ResponseEntity<List<ProductGraph>> getIndependentPipelines(@PathVariable("productId") Integer productId) {
//        return graphService.analyzeGraph(productId)
//    }
}

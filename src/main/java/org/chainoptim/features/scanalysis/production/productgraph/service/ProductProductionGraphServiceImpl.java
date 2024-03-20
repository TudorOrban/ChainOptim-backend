package org.chainoptim.features.scanalysis.production.productgraph.service;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.service.ProductService;
import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;
import org.chainoptim.features.scanalysis.production.productconnection.service.ProductStageConnectionService;
import org.chainoptim.features.scanalysis.production.productgraph.model.ProductGraph;
import org.chainoptim.features.scanalysis.production.productgraph.model.ProductProductionGraph;
import org.chainoptim.features.scanalysis.production.productgraph.repository.ProductProductionGraphRepository;
import org.chainoptim.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductProductionGraphServiceImpl implements ProductProductionGraphService {

    private final ProductProductionGraphRepository graphRepository;
    private final ProductService productService;
    private final ProductStageConnectionService productStageConnectionService;

    @Autowired
    public ProductProductionGraphServiceImpl(ProductProductionGraphRepository graphRepository,
                                             ProductService productService,
                                             ProductStageConnectionService productStageConnectionService
    ) {
        this.graphRepository = graphRepository;
        this.productService = productService;
        this.productStageConnectionService = productStageConnectionService;
    }


    public List<ProductProductionGraph> getProductionGraphByProductId(Integer productId) {
        return graphRepository.findProductionGraphByProductId(productId);
    }

    public ProductGraph createProductGraph(ProductGraph productGraph, Integer productId) {
        ProductProductionGraph productionGraph = new ProductProductionGraph();
        productionGraph.setProductGraph(productGraph);
        productionGraph.setProductId(productId);
        graphRepository.save(productionGraph);

        return productionGraph.getProductGraph();
    }

    public ProductProductionGraph generateProductGraph(Integer productId) {
        ProductProductionGraph productionGraph = new ProductProductionGraph();
        productionGraph.setProductId(productId);

        // Fetch product with its stages, stage connections
        Product product = productService.getProductWithStages(productId);
        List<ProductStageConnection> connections = productStageConnectionService.getConnectionsByProductId(productId);

        // Compute graph with latest product and connections
        ProductGraph updatedGraph = new ProductGraph();
        updatedGraph.populateGraph(product, connections);

        // Save updated graph
        productionGraph.setProductGraph(updatedGraph);
        graphRepository.save(productionGraph);

        return productionGraph;
    }

    // Update
    @Transactional
    public ProductProductionGraph updateProductGraph(Integer productId) {
        ProductProductionGraph productionGraph = graphRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Production graph with ID: " + productId + " not found."));

        // Fetch product with its stages, stage connections
        Product product = productService.getProductWithStages(productId);
        List<ProductStageConnection> connections = productStageConnectionService.getConnectionsByProductId(productId);

        // Recompute graph with fresh product and connections
        ProductGraph updatedGraph = new ProductGraph();
        updatedGraph.populateGraph(product, connections);

        // Save updated graph
        productionGraph.setProductGraph(updatedGraph);
        graphRepository.save(productionGraph);

        return productionGraph;
    }

    public ProductGraph saveProductGraph(Integer productionGraphId, ProductGraph productGraph) {
        ProductProductionGraph productionGraph = graphRepository.findById(productionGraphId)
                .orElseThrow(() -> new ResourceNotFoundException("Production graph with ID: " + productionGraphId + " not found."));

        productionGraph.setProductGraph(productGraph);

        graphRepository.save(productionGraph);

        return productionGraph.getProductGraph();
    }
}

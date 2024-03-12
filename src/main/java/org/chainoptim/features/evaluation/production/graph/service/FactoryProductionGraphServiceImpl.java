package org.chainoptim.features.evaluation.production.graph.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.graph.model.FactoryProductionGraph;
import org.chainoptim.features.evaluation.production.graph.repository.FactoryProductionGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoryProductionGraphServiceImpl implements FactoryProductionGraphService {

    private final FactoryProductionGraphRepository graphRepository;

    @Autowired
    public FactoryProductionGraphServiceImpl(FactoryProductionGraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public FactoryGraph createFactoryGraph(FactoryGraph factoryGraph, Integer factoryId) {
        FactoryProductionGraph productionGraph = new FactoryProductionGraph();
        productionGraph.setFactoryGraph(factoryGraph);
        productionGraph.setFactoryId(factoryId);
        graphRepository.save(productionGraph);

        return productionGraph.getFactoryGraph();
    }

    // Update
    public FactoryGraph saveFactoryGraph(Integer productionGraphId, FactoryGraph factoryGraph) {
        FactoryProductionGraph productionGraph = graphRepository.findById(productionGraphId)
                .orElseThrow(() -> new ResourceNotFoundException("Production graph with ID: " + productionGraphId + " not found."));

        productionGraph.setFactoryGraph(factoryGraph);

        graphRepository.save(productionGraph);

        return productionGraph.getFactoryGraph();
    }
}

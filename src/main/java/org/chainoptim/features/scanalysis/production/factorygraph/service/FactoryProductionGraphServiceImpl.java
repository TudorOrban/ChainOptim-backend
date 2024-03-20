package org.chainoptim.features.scanalysis.production.factorygraph.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.service.FactoryService;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.scanalysis.production.factoryconnection.service.FactoryStageConnectionService;
import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryProductionGraph;
import org.chainoptim.features.scanalysis.production.factorygraph.repository.FactoryProductionGraphRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryProductionGraphServiceImpl implements FactoryProductionGraphService {

    private final FactoryProductionGraphRepository graphRepository;
    private final FactoryService factoryService;
    private final FactoryStageConnectionService factoryStageConnectionService;

    @Autowired
    public FactoryProductionGraphServiceImpl(FactoryProductionGraphRepository graphRepository,
                                             FactoryService factoryService,
                                            FactoryStageConnectionService factoryStageConnectionService) {
        this.graphRepository = graphRepository;
        this.factoryService = factoryService;
        this.factoryStageConnectionService = factoryStageConnectionService;
    }


    public List<FactoryProductionGraph> getProductionGraphByFactoryId(Integer factoryId) {
        return graphRepository.findProductionGraphByFactoryId(factoryId);
    }

    public FactoryGraph createFactoryGraph(FactoryGraph factoryGraph, Integer factoryId) {
        FactoryProductionGraph productionGraph = new FactoryProductionGraph();
        productionGraph.setFactoryGraph(factoryGraph);
        productionGraph.setFactoryId(factoryId);
        graphRepository.save(productionGraph);

        return productionGraph.getFactoryGraph();
    }

    public FactoryProductionGraph generateFactoryGraph(Integer factoryId) {
        FactoryProductionGraph productionGraph = new FactoryProductionGraph();
        productionGraph.setFactoryId(factoryId);

        // Fetch factory with its stages, stage connections
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryStageConnection> connections = factoryStageConnectionService.getConnectionsByFactoryId(factoryId);

        // Compute graph with latest factory and connections
        FactoryGraph updatedGraph = new FactoryGraph();
        updatedGraph.populateGraph(factory, connections);

        // Save updated graph
        productionGraph.setFactoryGraph(updatedGraph);
        graphRepository.save(productionGraph);

        return productionGraph;
    }

    // Update
    @Transactional
    public FactoryProductionGraph updateFactoryGraph(Integer factoryId) {
        FactoryProductionGraph productionGraph = graphRepository.findById(factoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Production graph with ID: " + factoryId + " not found."));

        // Fetch factory with its stages, stage connections
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryStageConnection> connections = factoryStageConnectionService.getConnectionsByFactoryId(factoryId);

        // Recompute graph with fresh factory and connections
        FactoryGraph updatedGraph = new FactoryGraph();
        updatedGraph.populateGraph(factory, connections);

        // Save updated graph
        productionGraph.setFactoryGraph(updatedGraph);
        graphRepository.save(productionGraph);

        return productionGraph;
    }

    public FactoryGraph saveFactoryGraph(Integer productionGraphId, FactoryGraph factoryGraph) {
        FactoryProductionGraph productionGraph = graphRepository.findById(productionGraphId)
                .orElseThrow(() -> new ResourceNotFoundException("Production graph with ID: " + productionGraphId + " not found."));

        productionGraph.setFactoryGraph(factoryGraph);

        graphRepository.save(productionGraph);

        return productionGraph.getFactoryGraph();
    }
}

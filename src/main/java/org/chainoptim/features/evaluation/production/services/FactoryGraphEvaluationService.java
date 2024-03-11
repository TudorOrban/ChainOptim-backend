package org.chainoptim.features.evaluation.production.services;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.graph.model.Node;
import org.chainoptim.features.evaluation.production.model.FactoryStageConnection;
import org.chainoptim.features.evaluation.production.repository.AllocationPlan;
import org.chainoptim.features.evaluation.production.resourceallocation.service.ResourceAllocatorService;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.service.FactoryInventoryService;
import org.chainoptim.features.factory.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FactoryGraphEvaluationService {

    private final FactoryService factoryService;
    private final FactoryInventoryService factoryInventoryService;
    private final FactoryStageConnectionService factoryStageConnectionService;
    private final GraphService graphService;
    private final ResourceAllocatorService resourceAllocatorService;

    @Autowired
    public FactoryGraphEvaluationService(
            FactoryService factoryService,
            FactoryInventoryService factoryInventoryService,
            FactoryStageConnectionService factoryStageConnectionService,
            GraphService graphService,
            ResourceAllocatorService resourceAllocatorService) {
        this.factoryService = factoryService;
        this.factoryInventoryService = factoryInventoryService;
        this.factoryStageConnectionService = factoryStageConnectionService;
        this.graphService = graphService;
        this.resourceAllocatorService = resourceAllocatorService;
    }

    public AllocationPlan getFactoryPipelines(Integer factoryId, Float duration) {
        // Fetch factory with its stages, stage connections
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryStageConnection> connections = factoryStageConnectionService.getConnectionsByFactoryId(factoryId);

        // Create hashmap of component inventory levels
        List<FactoryInventoryItem> inventory = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);
        Map<Integer, FactoryInventoryItem> inventoryMap = inventory.stream()
                .filter(item -> item.getComponent() != null) // Only include items with a non-null component
                .collect(Collectors.toMap(item -> item.getComponent().getId(), item -> item));

        // Get data into FactoryGraph
        FactoryGraph factoryGraph = graphService.getStageGraph(factory, connections);

        // Sort by priority
        graphService.sortFactoryGraphNodesByPriority(factoryGraph);

        AllocationPlan allocationPlan = resourceAllocatorService.allocateResources(factoryGraph, inventoryMap, duration);


        // Split graph into connected components
        List<FactoryGraph> independentPipelines = graphService.splitIntoIndependentPipelines(factoryGraph);

        // Sort by pipeline priority
        independentPipelines.sort((p1, p2) -> Float.compare(p1.getPipelinePriority(), p2.getPipelinePriority()));


        Map<Integer, Float> totalComponentsBalance = inventoryMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getQuantity() // The quantity of the component
                ));

        for (FactoryGraph pipeline : independentPipelines) {
            analyzeIndependentPipeline(pipeline, totalComponentsBalance);
        }

        return allocationPlan;
    }


    public void analyzeIndependentPipeline(FactoryGraph pipeline, Map<Integer, Float> inventoryMap) {
        if (pipeline.getNodes().size() == 1) {
            analyzeIndepedentStage(pipeline.getNodes().get(0), inventoryMap);
        }


    }

    public void analyzeIndepedentStage(Node node, Map<Integer, Float> inventoryMap) {
        FactoryInventoryItem f = new FactoryInventoryItem();
    }
}

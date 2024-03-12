package org.chainoptim.features.evaluation.production.evaluation.service;

import org.chainoptim.features.evaluation.production.evaluation.model.TemporaryEvaluationType;
import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.graph.model.Node;
import org.chainoptim.features.evaluation.production.connection.model.FactoryStageConnection;
import org.chainoptim.features.evaluation.production.recommendation.service.FactoryResolutionRecommendationService;
import org.chainoptim.features.evaluation.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.evaluation.production.resourceallocation.model.DeficitResolverPlan;
import org.chainoptim.features.evaluation.production.resourceallocation.model.ResolutionEvaluation;
import org.chainoptim.features.evaluation.production.resourceallocation.model.ResolverPlanEvaluation;
import org.chainoptim.features.evaluation.production.resourceallocation.service.ResolutionEvaluationService;
import org.chainoptim.features.evaluation.production.resourceallocation.service.ResourceAllocatorService;
import org.chainoptim.features.evaluation.production.connection.service.FactoryStageConnectionService;
import org.chainoptim.features.evaluation.production.graph.service.FactoryPipelineService;
import org.chainoptim.features.evaluation.production.resourceallocation.service.ResourceSeekerService;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.service.FactoryInventoryService;
import org.chainoptim.features.factory.service.FactoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Service currently gathering all main evaluation components into a comprehensive flow in evaluateFactory.
 * Will be broken down with time as the frontend needs become clearer.
 *
 */
@Service
public class FactoryGraphEvaluationServiceImpl implements FactoryGraphEvaluationService {

    private final FactoryService factoryService;
    private final FactoryInventoryService factoryInventoryService;
    private final FactoryStageConnectionService factoryStageConnectionService;
    private final FactoryPipelineService factoryPipelineService;
    private final ResourceAllocatorService resourceAllocatorService;
    private final ResourceSeekerService resourceSeekerService;
    private final ResolutionEvaluationService resolutionEvaluationService;
    private final FactoryResolutionRecommendationService factoryResolutionRecommendationService;

    @Autowired
    public FactoryGraphEvaluationServiceImpl(
            FactoryService factoryService,
            FactoryInventoryService factoryInventoryService,
            FactoryStageConnectionService factoryStageConnectionService,
            FactoryPipelineService factoryPipelineService,
            ResourceAllocatorService resourceAllocatorService,
            ResourceSeekerService resourceSeekerService,
            ResolutionEvaluationService resolutionEvaluationService,
            FactoryResolutionRecommendationService factoryResolutionRecommendationService) {
        this.factoryService = factoryService;
        this.factoryInventoryService = factoryInventoryService;
        this.factoryStageConnectionService = factoryStageConnectionService;
        this.factoryPipelineService = factoryPipelineService;
        this.resourceAllocatorService = resourceAllocatorService;
        this.resourceSeekerService = resourceSeekerService;
        this.resolutionEvaluationService = resolutionEvaluationService;
        this.factoryResolutionRecommendationService = factoryResolutionRecommendationService;
    }

    public TemporaryEvaluationType evaluateFactory(Integer factoryId, Float duration) {
        // Fetch factory with its stages, stage connections
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryStageConnection> connections = factoryStageConnectionService.getConnectionsByFactoryId(factoryId);

        // Create hashmap of component inventory levels
        List<FactoryInventoryItem> inventory = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);
        Map<Integer, FactoryInventoryItem> inventoryMap = inventory.stream()
                .filter(item -> item.getComponent() != null) // Only include items with a non-null component
                .collect(Collectors.toMap(item -> item.getComponent().getId(), item -> item));

        // Get data into FactoryGraph
        FactoryGraph factoryGraph = factoryPipelineService.getStageGraph(factory, connections);

        // Sort by priority
        factoryPipelineService.sortFactoryGraphNodesByPriority(factoryGraph);

        // Plan allocation
        AllocationPlan allocationPlan = resourceAllocatorService.allocateResources(factoryGraph, inventoryMap, duration);

        // Identify deficit resolutions
        DeficitResolverPlan resolverPlan = resourceSeekerService.seekResources(factory.getOrganizationId(), allocationPlan.getAllocationDeficit(), factory.getLocation());

        // Evaluate plans
        ResolverPlanEvaluation planEvaluation = resolutionEvaluationService.evaluateResolverPlan(resolverPlan);

        // Make recommendations
        Map<Integer, List<ResolutionEvaluation>> recommendedResolutions = factoryResolutionRecommendationService.recommendResolverPlanActions(planEvaluation);

        return new TemporaryEvaluationType(allocationPlan, resolverPlan, planEvaluation, recommendedResolutions);
    }


    // Independent pipelines: not in use for now
    public void analyzeFactoryGraphWithPipelines(Integer factoryId, Float duration) {
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryStageConnection> connections = factoryStageConnectionService.getConnectionsByFactoryId(factoryId);

        // Create hashmap of component inventory levels
        List<FactoryInventoryItem> inventory = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);
        Map<Integer, FactoryInventoryItem> inventoryMap = inventory.stream()
                .filter(item -> item.getComponent() != null) // Only include items with a non-null component
                .collect(Collectors.toMap(item -> item.getComponent().getId(), item -> item));

        FactoryGraph factoryGraph = factoryPipelineService.getStageGraph(factory, connections);

        // Split graph into connected components
        List<FactoryGraph> independentPipelines = factoryPipelineService.splitIntoIndependentPipelines(factoryGraph);

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

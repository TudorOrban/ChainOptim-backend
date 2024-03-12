package org.chainoptim.features.evaluation.production.recommendation.service;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.connection.model.FactoryStageConnection;
import org.chainoptim.features.evaluation.production.recommendation.model.FactoryRecommendationReport;
import org.chainoptim.features.evaluation.production.connection.service.FactoryStageConnectionService;
import org.chainoptim.features.evaluation.production.graph.service.FactoryGraphService;
import org.chainoptim.features.factory.model.*;
import org.chainoptim.features.factory.service.FactoryInventoryService;
import org.chainoptim.features.factory.service.FactoryService;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/*
 * Service responsible for evaluating factory status, including
 * necessary component quantities vs inventory level, evaluation report, solutions
 * Will be refactored soon to use the new graph system
 *
 */
@Service
public class FactoryRecommendationServiceImpl implements FactoryRecommendationService {

    private final FactoryService factoryService;
    private final FactoryInventoryService factoryInventoryService;
    private final FactoryGraphService factoryGraphService;
    private final FactoryStageConnectionService factoryStageConnectionService;

    public FactoryRecommendationServiceImpl(
            FactoryService factoryService,
            FactoryInventoryService factoryInventoryService,
            FactoryGraphService factoryGraphService,
            FactoryStageConnectionService factoryStageConnectionService
    ) {
        this.factoryService = factoryService;
        this.factoryInventoryService = factoryInventoryService;
        this.factoryGraphService = factoryGraphService;
        this.factoryStageConnectionService = factoryStageConnectionService;
    }

    public FactoryRecommendationReport evaluateFactory(Integer factoryId) {
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryInventoryItem> inventory = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);

        return evaluateFactoryStatus(factory, inventory);
    }

    public FactoryGraph getFactoryPipelineGraph(Integer factoryId) {
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryStageConnection> connections = factoryStageConnectionService.getConnectionsByFactoryId(factoryId);
        return factoryGraphService.getStageGraph(factory, connections);
    }

    public FactoryRecommendationReport evaluateFactoryStatus(Factory factory, List<FactoryInventoryItem> inventory) {
        // Preparations
        FactoryRecommendationReport evaluationReport = new FactoryRecommendationReport();

        Map<Integer, FactoryInventoryItem> inventoryMap = inventory.stream()
                .filter(item -> item.getComponent() != null) // Only include items with a non-null component
                .collect(Collectors.toMap(item -> item.getComponent().getId(), item -> item));

        StringBuilder overallRecommendation = new StringBuilder();

        for(FactoryStage factoryStage : factory.getFactoryStages()) {
            FactoryRecommendationReport.StageReport stageReport = evaluateFactoryStage(factoryStage, inventoryMap);

            evaluationReport.getStageReports().add(stageReport);
            if (stageReport.getCapacityUtilizationPercentage() < 1) {
                overallRecommendation.append("Factory Stage ").append(factoryStage.getId()).append(" cannot work at full capacity\n");
            }
        }

        // Get recommendation
        if (overallRecommendation.isEmpty()) {
            evaluationReport.setOverallRecommendation("All factory stages work at full capacity");
        } else {
            evaluationReport.setOverallRecommendation(overallRecommendation.toString());
        }

        return evaluationReport;
    }

    private FactoryRecommendationReport.StageReport evaluateFactoryStage(FactoryStage factoryStage, Map<Integer, FactoryInventoryItem> inventoryMap) {
        FactoryRecommendationReport.StageReport stageReport = new FactoryRecommendationReport.StageReport();

        float numberOfStagesCapacity = factoryStage.getCapacity(); // Number of stages that can be executed in duration
        Float duration = factoryStage.getDuration();
        Float minimumCapacity = factoryStage.getMinimumRequiredCapacity();
        Stage stage = factoryStage.getStage();

        for(StageInput stageInput : stage.getStageInputs()) {
            FactoryRecommendationReport.InputReport inputReport = evaluateFactoryStageInput(stageInput, inventoryMap, numberOfStagesCapacity, stage.getName());

            stageReport.getInputReports().add(inputReport);
        }

        Optional<Float> minSurplusRatio = stageReport.getInputReports().stream()
                .map(FactoryRecommendationReport.InputReport::getSurplusComponentRatio)
                .min(Comparator.naturalOrder());
        float minimumSurplusRatio = minSurplusRatio.orElse(0.0f);

        // Prepare report
        stageReport.setStageId(stage.getId());
        stageReport.setStageName(stage.getName());
        stageReport.setCapacityUtilizationPercentage(minimumSurplusRatio);
        if (minimumSurplusRatio > 1) {
            stageReport.setStageRecommendation(
                    "Stage " + stage.getName() + " can work at full capacity for " + duration
            );
        } else if (minimumCapacity != null && minimumSurplusRatio > minimumCapacity) {
            stageReport.setStageRecommendation(
                    "Stage " + stage.getName() + " can work at minimum required capacity for " + duration + "but restock will be necessary afterwards"
            );
        } else {
            stageReport.setStageRecommendation(
                    "Stage " + stage.getName() + " can only work at " + (minimumSurplusRatio * 100) + " capacity for " + duration
            );
        }

        return stageReport;
    }

    private FactoryRecommendationReport.InputReport evaluateFactoryStageInput(StageInput stageInput, Map<Integer, FactoryInventoryItem> inventoryMap, Float numberOfStagesCapacity, String stageName) {
        Float componentQuantityPerStage = stageInput.getQuantity();
        Component inputComponent = stageInput.getComponent();
        float neededComponentQuantity = numberOfStagesCapacity * componentQuantityPerStage;

        // Set up missing component report
        FactoryRecommendationReport.InputReport missingComponentReport = getMissingComponentReport(neededComponentQuantity, inputComponent);

        // Find corresponding item in factory inventory or return missing report
        FactoryInventoryItem inventoryComponent = inventoryMap.get(inputComponent.getId());
        if (inventoryComponent == null) return missingComponentReport;
        Float inventoryComponentQuantity = inventoryComponent.getQuantity();
        if (inventoryComponentQuantity == null) return missingComponentReport;

        // Compute surplus
        float surplusComponentQuantity = inventoryComponentQuantity - neededComponentQuantity;
        float surplusRatio = inventoryComponentQuantity / neededComponentQuantity;

        // Set up input report
        FactoryRecommendationReport.InputReport inputReport = new FactoryRecommendationReport.InputReport();
        inputReport.setComponentId(inputComponent.getId());
        inputReport.setComponentName(inputComponent.getName());
        inputReport.setSurplusComponentQuantity(surplusComponentQuantity);
        inputReport.setSurplusComponentRatio(surplusRatio);
        if (surplusComponentQuantity > 0) {
            inputReport.setInputRecommendation(
                    "Stage " + stageName + " has necessary quantity of component " + inputComponent.getName()
            );
        } else {
            inputReport.setInputRecommendation(
                    "Stage " + stageName + " requires " + (-surplusComponentQuantity) + " more of component " + inputComponent.getName() + " to work at full capacity"
            );
        }

        return inputReport;
    }

    private FactoryRecommendationReport.InputReport getMissingComponentReport(float neededComponentQuantity, Component inputComponent) {
        FactoryRecommendationReport.InputReport missingComponentReport = new FactoryRecommendationReport.InputReport();
        missingComponentReport.setSurplusComponentQuantity(-neededComponentQuantity);
        missingComponentReport.setSurplusComponentRatio(0.0f);
        missingComponentReport.setInputRecommendation(
                "Restock " + inputComponent.getName() + " immediately: factory requires " + (-neededComponentQuantity) + " to work at full capacity"
        );
        return missingComponentReport;
    }
}

package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryEvaluationReport;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FactoryEvaluationServiceImpl implements FactoryEvaluationService {

    private final FactoryService factoryService;
    private final FactoryInventoryService factoryInventoryService;
    private static final Logger logger = LoggerFactory.getLogger(FactoryEvaluationServiceImpl.class);

    public FactoryEvaluationServiceImpl(
            FactoryService factoryService,
            FactoryInventoryService factoryInventoryService
    ) {
        this.factoryService = factoryService;
        this.factoryInventoryService = factoryInventoryService;
    }

    public FactoryEvaluationReport evaluateFactory(Integer factoryId) {
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryInventoryItem> inventory = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);

        return evaluateFactoryStatus(factory, inventory);
    }

    public FactoryEvaluationReport evaluateFactoryStatus(Factory factory, List<FactoryInventoryItem> inventory) {
        // Preparations
        FactoryEvaluationReport evaluationReport = new FactoryEvaluationReport();

        Map<Integer, FactoryInventoryItem> inventoryMap = inventory.stream()
                .filter(item -> item.getComponent() != null) // Only include items with a non-null component
                .collect(Collectors.toMap(item -> item.getComponent().getId(), item -> item));

        for(FactoryStage factoryStage : factory.getFactoryStages()) {
            FactoryEvaluationReport.StageReport stageReport = evaluateFactoryStage(factoryStage, inventoryMap);

            evaluationReport.getStageReports().add(stageReport);

        }

        return evaluationReport;
    }

    private FactoryEvaluationReport.StageReport evaluateFactoryStage(FactoryStage factoryStage, Map<Integer, FactoryInventoryItem> inventoryMap) {
        FactoryEvaluationReport.StageReport stageReport = new FactoryEvaluationReport.StageReport();

        float numberOfStagesCapacity = factoryStage.getCapacity(); // Number of stages that can be executed in duration
        Float duration = factoryStage.getDuration();
        Float minimumCapacity = factoryStage.getMinimumRequiredCapacity();
        Stage stage = factoryStage.getStage();


        for(StageInput stageInput : stage.getStageInputs()) {
            FactoryEvaluationReport.InputReport inputReport = evaluateFactoryStageInput(stageInput, inventoryMap, numberOfStagesCapacity, stage.getName());

            stageReport.getInputReports().add(inputReport);
        }

        Optional<Float> minSurplusRatio = stageReport.getInputReports().stream()
                .map(FactoryEvaluationReport.InputReport::getSurplusComponentRatio)
                .min(Comparator.naturalOrder());

        Float minimumSurplusRatio = 0.0f;
        if (minSurplusRatio.isPresent()) {
            minimumSurplusRatio = minSurplusRatio.get();
        }

        // Prepare report
        stageReport.setStageId(stage.getId());
        stageReport.setStageName(stage.getName());
        stageReport.setCapacityUtilizationPercentage(minimumSurplusRatio);
        if (minimumSurplusRatio > 1) {
            stageReport.setStageRecommendation(
                    "Stage " + stage.getName() + " can work at full capacity"
            );
        } else {
            stageReport.setStageRecommendation(
                    "Stage " + stage.getName() + " only works at " + (minimumSurplusRatio * 100) + " capacity"
            );
        }

        return stageReport;
    }

    private FactoryEvaluationReport.InputReport evaluateFactoryStageInput(StageInput stageInput, Map<Integer, FactoryInventoryItem> inventoryMap, Float numberOfStagesCapacity, String stageName) {
        Float componentQuantityPerStage = stageInput.getQuantity();
        Component inputComponent = stageInput.getComponent();

        // Find corresponding item in factory inventory
        FactoryInventoryItem inventoryComponent = inventoryMap.get(inputComponent.getId());
        if (inventoryComponent == null) return new FactoryEvaluationReport.InputReport();
        Float inventoryComponentQuantity = inventoryComponent.getQuantity();
        if (inventoryComponentQuantity == null) return new FactoryEvaluationReport.InputReport();

        logger.info("Number of stages capacity: {}, Copmponent Quantity per stage: {}", numberOfStagesCapacity, componentQuantityPerStage);
        float neededComponentQuantity = numberOfStagesCapacity * componentQuantityPerStage;
        float surplusComponentQuantity = inventoryComponentQuantity - neededComponentQuantity;
        float surplusRatio = inventoryComponentQuantity / neededComponentQuantity;
        float surplusPercentage = surplusRatio * 100;

        logger.info("Able to work at {}% capacity for stage input {}, with surplus {}", surplusPercentage, inventoryComponent.getId(), surplusComponentQuantity);

        // Set up input report
        FactoryEvaluationReport.InputReport inputReport = new FactoryEvaluationReport.InputReport();
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
}

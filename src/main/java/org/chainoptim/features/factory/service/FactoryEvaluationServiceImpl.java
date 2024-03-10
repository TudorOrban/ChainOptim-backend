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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public void evaluateFactory(Integer factoryId) {
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        List<FactoryInventoryItem> inventory = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);

        evaluateFactoryStatus(factory, inventory);
    }

    public void evaluateFactoryStatus(Factory factory, List<FactoryInventoryItem> inventory) {
        FactoryEvaluationReport evaluationReport = new FactoryEvaluationReport();

        // Preparations
        Map<Integer, FactoryInventoryItem> inventoryMap = inventory.stream()
                .filter(item -> item.getComponent() != null) // Only include items with a non-null component
                .collect(Collectors.toMap(item -> item.getComponent().getId(), item -> item));

        for(FactoryStage factoryStage : factory.getFactoryStages()) {
            float numberOfStagesCapacity = factoryStage.getCapacity(); // Number of stages that can be executed in duration
//            Float duration = factoryStage.getDuration();
            Float minimumCapacity = factoryStage.getMinimumRequiredCapacity();
            Stage stage = factoryStage.getStage();

            FactoryEvaluationReport.StageReport stageReport = new FactoryEvaluationReport.StageReport();


            for(StageInput stageInput : stage.getStageInputs()) {
                evaluateFactoryStageInput(stageInput, inventoryMap, numberOfStagesCapacity);

            }
        }
    }

    private void evaluateFactoryStageInput(StageInput stageInput, Map<Integer, FactoryInventoryItem> inventoryMap, Float numberOfStagesCapacity) {
        Float componentQuantityPerStage = stageInput.getQuantity();
        Component inputComponent = stageInput.getComponent();

        // Find corresponding item in factory inventory
        FactoryInventoryItem inventoryComponent = inventoryMap.get(inputComponent.getId());
        if (inventoryComponent == null) return;
        Float inventoryComponentQuantity = inventoryComponent.getQuantity();
        if (inventoryComponentQuantity == null) return;

        float neededComponentQuantity = numberOfStagesCapacity * componentQuantityPerStage;
        float surplusComponentQuantity = inventoryComponentQuantity - neededComponentQuantity;
        float surplusRatio = inventoryComponentQuantity / neededComponentQuantity;
        float surplusPercentage = surplusRatio * 100;

        logger.info("Able to work at {}% capacity for stage input {}, with surplus {}", surplusPercentage, inventoryComponent.getId(), surplusComponentQuantity);
    }
}

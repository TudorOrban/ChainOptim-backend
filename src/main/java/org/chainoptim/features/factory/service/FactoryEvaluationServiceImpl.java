package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.model.StageInput;

import java.util.List;

public class FactoryEvaluationServiceImpl implements FactoryEvaluationService {

    public void evaluateFactoryStatus(Factory factory, List<FactoryInventoryItem> inventory, List<Component> organizationComponents) {
        for(FactoryStage factoryStage : factory.getFactoryStages()) {
            Double factoryCapacity = factoryStage.getCapacity();
            Double duration = factoryStage.getDuration();
            Stage stage = factoryStage.getStage();

            for(StageInput stageInput : stage.getStageInputs()) {
//                Component stageComponent = organizationComponents.stream().findAny(c -> c.getId() = stageInput.getComponentId());
            }
        }
    }
}

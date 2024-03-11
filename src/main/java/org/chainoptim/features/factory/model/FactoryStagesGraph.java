package org.chainoptim.features.factory.model;

import org.chainoptim.features.evaluation.production.model.FactoryStageConnection;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.chainoptim.features.productpipeline.model.StageOutput;

import java.util.List;
import java.util.Set;

public class FactoryStagesGraph {

    public void getStageGraph(Factory factory, List<FactoryStageConnection> connections) {
//        Graph graph = new Graph();
//
//        for (FactoryStage factoryStage : factory.getFactoryStages()) {
//            Stage stage = factoryStage.getStage();
//            float capacity = factoryStage.getCapacity();
//            graph.addNode(stage, capacity);
//
//            Map<Integer, Float> inputs = new HashMap<>();
//            for (StageInput stageInput : stage.getStageInputs()) {
//                inputs.put(stageInput.getId(), stageInput.getQuantity());
//                graph.getInputToStageIdMap().put(stageInput.getId(), stage.getId());
//            }
//
//            Map<Integer, Float> outputs = new HashMap<>();
//            for (StageOutput stageOutput : stage.getStageOutputs()) {
//                outputs.put(stageOutput.getId(), stageOutput.getQuantity());
//                graph.getOutputToStageIdMap().put(stageOutput.getId(), stage.getId());
//            }
//
//            graph.registerInputOutputMappings(stage, inputs, outputs);
//        }
//
//        // Add edges based on connections
//        for (FactoryStageConnection connection : connections) {
//            graph.addEdge(connection.getOutgoingStageInputId(), connection.getIncomingStageOutputId());
//        }








        Set<FactoryStage> factoryStages = factory.getFactoryStages();

        for (FactoryStage factoryStage : factoryStages) {
            Float numberOfStagesCapacity = factoryStage.getCapacity();
            Stage stage = factoryStage.getStage();
            Integer stageId = stage.getId();


            for (StageInput stageInput : stage.getStageInputs()) {
                Integer stageInputId = stageInput.getId();
                Float neededQuantityPerStage = stageInput.getQuantity();
                Component component = stageInput.getComponent();
            }

            for (StageOutput stageOutput : stage.getStageOutputs()) {
                Integer stageOutputId = stageOutput.getId();
                Float outputedQuantityPerStage = stageOutput.getQuantity();
                Integer componentId = stageOutput.getComponent().getId();
            }
        }

    }
}

package org.chainoptim.features.scanalysis.production.factorygraph.model;

import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.chainoptim.features.productpipeline.model.StageOutput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactoryGraph {
    private Map<Integer, StageNode> nodes = new HashMap<>(); // Key: factoryStageId
    private Map<Integer, List<Edge>> adjList = new HashMap<>(); // Key: factoryStageId
    private Float pipelinePriority;

    public void populateGraph(Factory factory, List<FactoryStageConnection> connections) {
        for (FactoryStage factoryStage : factory.getFactoryStages()) {
            StageNode node = new StageNode();
            List<Edge> newEdges = new ArrayList<>();

            SmallStage stage = new SmallStage();
            List<SmallStageInput> stageInputs = new ArrayList<>();
            List<SmallStageOutput> stageOutputs = new ArrayList<>();

            for (StageInput stageInput : factoryStage.getStage().getStageInputs()) {
                System.out.println("Stage Input: " + stageInput);
                // Transform to small stage input and add
                SmallStageInput smallStageInput = new SmallStageInput();
                smallStageInput.setId(stageInput.getId());
                smallStageInput.setQuantityPerStage(stageInput.getQuantity());
                smallStageInput.setComponentId(stageInput.getComponent().getId());
                smallStageInput.setComponentName(stageInput.getComponent().getName());

                stageInputs.add(smallStageInput);
            }

            for (StageOutput stageOutput : factoryStage.getStage().getStageOutputs()) {
                // Transform to small stage output and add
                SmallStageOutput smallStageOutput = new SmallStageOutput();
                smallStageOutput.setId(stageOutput.getId());
                smallStageOutput.setQuantityPerStage(stageOutput.getQuantity());
                smallStageOutput.setComponentId(stageOutput.getComponent().getId());
                smallStageOutput.setComponentName(stageOutput.getComponent().getName());

                stageOutputs.add(smallStageOutput);

                // Add outgoing neighbors to adjList
                List<FactoryStageConnection> adjacentConnections = connections.stream()
                        .filter(c -> Objects.equals(c.getIncomingStageOutputId(), stageOutput.getId())).toList();

                for (FactoryStageConnection factoryStageConnection : adjacentConnections) {
                    Edge newEdge = new Edge(
                            factoryStageConnection.getIncomingFactoryStageId(),
                            factoryStageConnection.getIncomingStageOutputId(),
                            factoryStageConnection.getOutgoingFactoryStageId(),
                            factoryStageConnection.getOutgoingStageInputId());
                    newEdges.add(newEdge);
                }
            }

            // Set stage
            stage.setId(factoryStage.getId());
            stage.setStageName(factoryStage.getStage().getName());
            stage.setStageInputs(stageInputs);
            stage.setStageOutputs(stageOutputs);

            // Set node
            node.setSmallStage(stage);
            node.setNumberOfStepsCapacity(factoryStage.getCapacity());
            node.setMinimumRequiredCapacity(factoryStage.getMinimumRequiredCapacity());
            node.setPriority(factoryStage.getPriority());
            node.setPerDuration(factoryStage.getDuration());

            nodes.put(stage.getId(), node);
            adjList.put(factoryStage.getId(), newEdges);
        }


    }

}





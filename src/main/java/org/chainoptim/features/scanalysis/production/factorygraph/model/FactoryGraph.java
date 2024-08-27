package org.chainoptim.features.scanalysis.production.factorygraph.model;

import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.production.model.Factory;
import org.chainoptim.features.production.model.FactoryStage;
import org.chainoptim.features.goods.stage.model.StageInput;
import org.chainoptim.features.goods.stage.model.StageOutput;

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
            if (factoryStage.getStage() == null) {
                continue;
            }

            StageNode node = new StageNode();
            List<Edge> newEdges = new ArrayList<>();

            SmallStage stage = new SmallStage();
            List<SmallStageInput> stageInputs = getSmallStageInputs(factoryStage);
            List<SmallStageOutput> stageOutputs = getSmallStageOutputs(factoryStage, connections, newEdges);

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

    private List<SmallStageInput> getSmallStageInputs(FactoryStage factoryStage) {
        if (factoryStage.getStage().getStageInputs() == null) {
            return new ArrayList<>();
        }
        List<SmallStageInput> stageInputs = new ArrayList<>();

        for (StageInput stageInput : factoryStage.getStage().getStageInputs()) {
            System.out.println("Stage Input: " + stageInput);
            // Transform to small stage input and add
            SmallStageInput smallStageInput = new SmallStageInput();
            smallStageInput.setId(stageInput.getId());
            smallStageInput.setQuantityPerStage(stageInput.getQuantity());
            if (stageInput.getComponent() != null) {
                smallStageInput.setComponentId(stageInput.getComponent().getId());
                smallStageInput.setComponentName(stageInput.getComponent().getName());
            }

            stageInputs.add(smallStageInput);
        }

        return stageInputs;
    }

    private List<SmallStageOutput> getSmallStageOutputs(FactoryStage factoryStage, List<FactoryStageConnection> connections, List<Edge> newEdges) {
        if (factoryStage.getStage().getStageOutputs() == null) {
            return new ArrayList<>();
        }
        List<SmallStageOutput> stageOutputs = new ArrayList<>();

        for (StageOutput stageOutput : factoryStage.getStage().getStageOutputs()) {
            // Transform to small stage output and add
            SmallStageOutput smallStageOutput = new SmallStageOutput();
            smallStageOutput.setId(stageOutput.getId());
            smallStageOutput.setQuantityPerStage(stageOutput.getQuantity());
            if (stageOutput.getComponent() != null) {
                smallStageOutput.setComponentId(stageOutput.getComponent().getId());
                smallStageOutput.setComponentName(stageOutput.getComponent().getName());
            } else if (stageOutput.getProductId() != null) {
                smallStageOutput.setProductId(stageOutput.getProductId());
            }

            stageOutputs.add(smallStageOutput);

            // Add outgoing neighbors to adjList
            List<FactoryStageConnection> adjacentConnections = connections.stream()
                    .filter(c -> Objects.equals(c.getSrcStageOutputId(), stageOutput.getId())).toList();

            for (FactoryStageConnection factoryStageConnection : adjacentConnections) {
                Edge newEdge = new Edge(
                        factoryStageConnection.getSrcFactoryStageId(),
                        factoryStageConnection.getSrcStageOutputId(),
                        factoryStageConnection.getDestFactoryStageId(),
                        factoryStageConnection.getDestStageInputId());
                newEdges.add(newEdge);
            }
        }

        return stageOutputs;
    }

}





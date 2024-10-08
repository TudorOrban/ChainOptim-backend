package org.chainoptim.features.goods.productgraph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.features.goods.product.model.Product;
import org.chainoptim.features.goods.stage.model.Stage;
import org.chainoptim.features.goods.stage.model.StageInput;
import org.chainoptim.features.goods.stage.model.StageOutput;
import org.chainoptim.features.production.factorygraph.model.SmallStage;
import org.chainoptim.features.production.factorygraph.model.SmallStageInput;
import org.chainoptim.features.production.factorygraph.model.SmallStageOutput;
import org.chainoptim.features.goods.stageconnection.model.ProductStageConnection;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGraph {
    private Map<Integer, SmallStage> nodes = new HashMap<>(); // Key: stageId
    private Map<Integer, List<ProductEdge>> adjList = new HashMap<>(); // Key: stageId

    public void populateGraph(Product product, List<ProductStageConnection> connections) {
        for (Stage productStage : product.getStages()) {
            List<ProductEdge> newEdges = new ArrayList<>();

            SmallStage stage = new SmallStage();
            List<SmallStageInput> stageInputs = getSmallStageInputs(productStage);
            List<SmallStageOutput> stageOutputs = getSmallStageOutputs(productStage, connections, newEdges);

            // Set stage
            stage.setId(productStage.getId());
            stage.setStageName(productStage.getName());
            stage.setStageInputs(stageInputs);
            stage.setStageOutputs(stageOutputs);

            nodes.put(stage.getId(), stage);
            adjList.put(stage.getId(), newEdges);
        }
    }

    private List<SmallStageInput> getSmallStageInputs(Stage stage) {
        if (stage.getStageInputs() == null) {
            return new ArrayList<>();
        }
        List<SmallStageInput> stageInputs = new ArrayList<>();

        for (StageInput stageInput : stage.getStageInputs()) {
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

    private List<SmallStageOutput> getSmallStageOutputs(Stage stage, List<ProductStageConnection> connections, List<ProductEdge> newEdges) {
        if (stage.getStageOutputs() == null) {
            return new ArrayList<>();
        }
        List<SmallStageOutput> stageOutputs = new ArrayList<>();

        for (StageOutput stageOutput : stage.getStageOutputs()) {
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
            List<ProductStageConnection> adjacentConnections = connections.stream()
                    .filter(c -> Objects.equals(c.getSrcStageOutputId(), stageOutput.getId())).toList();

            for (ProductStageConnection productStageConnection : adjacentConnections) {
                ProductEdge newEdge = new ProductEdge(
                        productStageConnection.getSrcStageId(),
                        productStageConnection.getSrcStageOutputId(),
                        productStageConnection.getDestStageId(),
                        productStageConnection.getDestStageInputId());
                newEdges.add(newEdge);
            }
        }

        return stageOutputs;
    }
}

package org.chainoptim.features.scanalysis.production.productgraph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.chainoptim.features.productpipeline.model.StageOutput;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.scanalysis.production.factorygraph.model.*;
import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;

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
            List<SmallStageInput> stageInputs = new ArrayList<>();
            List<SmallStageOutput> stageOutputs = new ArrayList<>();

            for (StageInput stageInput : productStage.getStageInputs()) {
                // Transform to small stage input and add
                SmallStageInput smallStageInput = new SmallStageInput();
                smallStageInput.setId(stageInput.getId());
                smallStageInput.setQuantityPerStage(stageInput.getQuantity());
                smallStageInput.setComponentId(stageInput.getComponent().getId());
                smallStageInput.setComponentName(stageInput.getComponent().getName());

                stageInputs.add(smallStageInput);
            }

            for (StageOutput stageOutput : productStage.getStageOutputs()) {
                // Transform to small stage output and add
                SmallStageOutput smallStageOutput = new SmallStageOutput();
                smallStageOutput.setId(stageOutput.getId());
                smallStageOutput.setQuantityPerStage(stageOutput.getQuantity());
                if (stageOutput.getProductId() != null) {
                    smallStageOutput.setProductId(stageOutput.getProductId());
                } else if (stageOutput.getComponent() != null) {
                    smallStageOutput.setComponentId(stageOutput.getComponent().getId());
                    smallStageOutput.setComponentName(stageOutput.getComponent().getName());
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

            // Set stage
            stage.setId(productStage.getId());
            stage.setStageName(productStage.getName());
            stage.setStageInputs(stageInputs);
            stage.setStageOutputs(stageOutputs);

            nodes.put(stage.getId(), stage);
            adjList.put(stage.getId(), newEdges);
        }


    }
}

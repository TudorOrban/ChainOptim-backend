package org.chainoptim.features.evaluation.production.resourceallocation.service;

import org.chainoptim.features.evaluation.production.graph.model.*;
import org.chainoptim.features.evaluation.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.evaluation.production.resourceallocation.model.ResourceAllocation;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceAllocatorServiceImpl implements ResourceAllocatorService {

    public AllocationPlan allocateResources(FactoryGraph factoryGraph,
                                            Map<Integer, FactoryInventoryItem> inventoryMap,
                                            Float duration) {
        Map<Integer, FactoryInventoryItem> inventoryBalance = new HashMap<>(inventoryMap);
        List<ResourceAllocation> allocationDeficit = new ArrayList<>();

        // Allocate resources for each stage by priority
        for (Map.Entry<Integer, Node> nodeEntry : factoryGraph.getNodes().entrySet()) {
            Integer stageId = nodeEntry.getKey();
            Node node = nodeEntry.getValue();

            // Compute number of steps in prescribed duration
            Float numberOfStepsCapacity = node.getNumberOfStepsCapacity();
            float perDuration = node.getPerDuration();
            float durationRatio = duration / perDuration;
            float totalNumberOfStepsCapacity = numberOfStepsCapacity * durationRatio;

            // Allocate resources for each stage input
            for (SmallStageInput stageInput : node.getSmallStage().getStageInputs()) {
                Float neededQuantity = stageInput.getQuantityPerStage() * totalNumberOfStepsCapacity;

                FactoryInventoryItem componentItem = inventoryBalance.get(stageInput.getComponentId());

                // Start allocating resource
                ResourceAllocation allocation = new ResourceAllocation();
                allocation.setStageInputId(stageInput.getId());
                allocation.setComponentId(stageInput.getComponentId());
                allocation.setRequestedAmount(neededQuantity);

                if (componentItem == null) {
                    allocation.setAllocatorInventoryItemId(-1);
                    allocation.setAllocatedAmount(0.0f);
                    allocationDeficit.add(allocation);
                    continue; // Skip further processing for this stage input
                }

                // Compute surplus
                float surplus = componentItem.getQuantity() - neededQuantity;
                boolean isSurplus = surplus >= 0;

                // Compute and set allocated quantity
                Float allocatedQuantity = isSurplus ? neededQuantity : componentItem.getQuantity();
                stageInput.setAllocatedQuantity(allocatedQuantity);

                // Update the inventory balance
                Float newQuantity = isSurplus ? surplus : 0.0f;
                componentItem.setQuantity(newQuantity);
                inventoryBalance.put(stageInput.getComponentId(), componentItem);

                if (!isSurplus) {
                    allocation.setAllocatorInventoryItemId(componentItem.getId());
                    allocation.setAllocatedAmount(componentItem.getQuantity());
                    allocationDeficit.add(allocation);
                }
            }

            // Retrieve the outgoing resources
            // Calculate total input quantities based on allocated quantities
            computeExpectedStageOutputs(node, durationRatio);

            // Get neighbors
            List<Edge> nodeNeighbors = factoryGraph.getAdjList().get(stageId);

            // Updated inventoryBalance with expected outputs for connected stages
            for (SmallStageOutput stageOutput : node.getSmallStage().getStageOutputs()) {
                FactoryInventoryItem inventoryItem = inventoryBalance.get(stageId);
                List<Edge> outputNeighbors = nodeNeighbors.stream()
                        .filter(nn -> Objects.equals(nn.getIncomingStageOutputId(), stageOutput.getId())).toList();

                if (outputNeighbors.size() == 1) { // Ignore > 1 for now
                    inventoryItem.setQuantity(inventoryItem.getQuantity() + stageOutput.getExpectedOutputPerAllocation());
                }
            }
        }

        return new AllocationPlan(factoryGraph, inventoryBalance, allocationDeficit);
    }

    private void computeExpectedStageOutputs(Node node, float durationRatio) {
        float totalAllocatedInput = node.getSmallStage().getStageInputs().stream()
                .map(SmallStageInput::getAllocatedQuantity)
                .reduce(0.0f, Float::sum);

        float totalInitialOutputs = node.getSmallStage().getStageOutputs().stream()
                .map(SmallStageOutput::getQuantityPerStage)
                .reduce(0.0f, Float::sum);

        float totalInitialInputs = node.getSmallStage().getStageInputs().stream()
                .map(SmallStageInput::getQuantityPerStage)
                .reduce(0.0f, Float::sum);

        // Get input-output ratio (adjusting for prescribed duration)
        float inputOutputRatio = (totalInitialOutputs / totalInitialInputs) * durationRatio;

        // Compute expected output
        float expectedOutput = inputOutputRatio * totalAllocatedInput;

        for (SmallStageOutput output : node.getSmallStage().getStageOutputs()) {
            // Distribute expected output
            float outputRatio = output.getQuantityPerStage() / totalInitialOutputs;
            float expectedOutputPerAllocation = expectedOutput * outputRatio;

            output.setExpectedOutputPerAllocation(expectedOutputPerAllocation);
        }
    }
}

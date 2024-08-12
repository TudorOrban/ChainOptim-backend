package org.chainoptim.features.scanalysis.production.resourceallocation.service;

import org.chainoptim.features.scanalysis.production.factorygraph.model.*;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.AllocationResult;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocation;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceAllocatorServiceImpl implements ResourceAllocatorService {


    public AllocationPlan allocateResourcesFromInventory(FactoryGraph factoryGraph,
                                            Map<Integer, FactoryInventoryItem> inventoryMap,
                                            Float duration) {
        Map<Integer, FactoryInventoryItem> inventoryBalance = new HashMap<>(inventoryMap);
        List<ResourceAllocation> allocations = new ArrayList<>();
        List<AllocationResult> results = new ArrayList<>();

        // Allocate resources for each stage by priority
        for (Map.Entry<Integer, StageNode> nodeEntry : factoryGraph.getNodes().entrySet()) {
            Integer factoryStageId = nodeEntry.getKey();
            StageNode node = nodeEntry.getValue();

            // Compute number of steps in prescribed duration
            Float numberOfStepsCapacity = node.getNumberOfStepsCapacity();
            float perDuration = node.getPerDuration();
            float durationRatio = duration / perDuration;
            float totalNumberOfStepsCapacity = numberOfStepsCapacity * durationRatio;

            List<Float> allocatedRequestedRatios = new ArrayList<>();

            // Allocate resources for each stage input
            for (SmallStageInput stageInput : node.getSmallStage().getStageInputs()) {
                allocateStageInputResources(stageInput, factoryStageId, node,
                        totalNumberOfStepsCapacity, allocations, allocatedRequestedRatios,
                        inventoryBalance);
            }

            // Calculate how many steps can be executed
            float minimumRatio =  allocatedRequestedRatios.stream().min(Float::compare).orElse(0.0f);
            node.setAllocationCapacityRatio(minimumRatio);

            // Retrieve the outgoing resources
            // - Calculate total input quantities based on allocated quantities
            computeExpectedAndRequestedStageOutputs(node, factoryStageId, durationRatio, results);

            List<Edge> nodeNeighbors = factoryGraph.getAdjList().get(factoryStageId);

            // - Update inventoryBalance with expected outputs for connected stages
            for (SmallStageOutput stageOutput : node.getSmallStage().getStageOutputs()) {
                FactoryInventoryItem inventoryItem = inventoryBalance.get(factoryStageId);
                if (inventoryItem == null) {
                    continue;
                }

                List<Edge> outputNeighbors = nodeNeighbors.stream()
                        .filter(nn -> Objects.equals(nn.getSrcStageOutputId(), stageOutput.getId())).toList();
                if (outputNeighbors.size() == 1) { // Ignore > 1 for now
                    inventoryItem.setQuantity(inventoryItem.getQuantity() + stageOutput.getExpectedOutputPerAllocation());
                }
            }
        }

        return new AllocationPlan(factoryGraph, inventoryBalance, allocations, results, duration);
    }

    private void allocateStageInputResources(SmallStageInput stageInput, Integer factoryStageId, StageNode node,
                                    float totalNumberOfStepsCapacity, List<ResourceAllocation> allocations, List<Float> allocatedRequestedRatios,
                                    Map<Integer, FactoryInventoryItem> inventoryBalance) {
        Float neededQuantity = stageInput.getQuantityPerStage() * totalNumberOfStepsCapacity;

        FactoryInventoryItem componentItem = inventoryBalance.get(stageInput.getComponentId());

        // Start allocating resource
        ResourceAllocation allocation = new ResourceAllocation();
        allocation.setStageInputId(stageInput.getId());
        allocation.setFactoryStageId(factoryStageId);
        allocation.setStageName(node.getSmallStage().getStageName());
        allocation.setComponentId(stageInput.getComponentId());
        allocation.setComponentName(stageInput.getComponentName());
        allocation.setRequestedAmount(neededQuantity);

        if (componentItem == null) {
            allocation.setAllocatorInventoryItemId(-1);
            allocation.setAllocatedAmount(0.0f);
            allocations.add(allocation);
            return;
        }

        // Compute surplus
        float surplus = componentItem.getQuantity() - neededQuantity;
        boolean isSurplus = surplus >= 0;

        // Compute and set allocated quantity
        Float allocatedQuantity = isSurplus ? neededQuantity : componentItem.getQuantity();
        stageInput.setAllocatedQuantity(allocatedQuantity);
        stageInput.setRequestedQuantity(neededQuantity);

        // Update the inventory balance
        Float newQuantity = isSurplus ? surplus : 0.0f;
        componentItem.setQuantity(newQuantity);
        inventoryBalance.put(stageInput.getComponentId(), componentItem);

        // Add allocation
        allocation.setAllocatorInventoryItemId(componentItem.getId());
        allocation.setAllocatedAmount(allocatedQuantity);
        allocations.add(allocation);

        allocatedRequestedRatios.add(allocatedQuantity / neededQuantity);
    }

    public void computeExpectedAndRequestedStageOutputs(StageNode node, Integer factoryStageId, float durationRatio, List<AllocationResult> results) {
        float totalAllocatedInput = node.getSmallStage().getStageInputs().stream()
                .map(SmallStageInput::getAllocatedQuantity)
                .reduce(0.0f, Float::sum);
        float totalRequestedInput = node.getSmallStage().getStageInputs().stream()
                .map(SmallStageInput::getRequestedQuantity)
                .reduce(0.0f, Float::sum);

        float totalInitialOutputs = node.getSmallStage().getStageOutputs().stream()
                .map(SmallStageOutput::getQuantityPerStage)
                .reduce(0.0f, Float::sum);

        float totalInitialInputs = node.getSmallStage().getStageInputs().stream()
                .map(SmallStageInput::getQuantityPerStage)
                .reduce(0.0f, Float::sum);

        // Get input-output ratio (adjusting for prescribed duration)
        float inputOutputRatio = (totalInitialOutputs / totalInitialInputs) * durationRatio;

        // Compute expected and requested output
        float expectedOutput = inputOutputRatio * totalAllocatedInput;
        float requestedOutput = inputOutputRatio * totalRequestedInput;

        for (SmallStageOutput output : node.getSmallStage().getStageOutputs()) {
            // Distribute expected output
            float outputRatio = output.getQuantityPerStage() / totalInitialOutputs;
            float expectedOutputPerAllocation = expectedOutput * outputRatio;
            float outputRequest = requestedOutput * outputRatio;

            output.setExpectedOutputPerAllocation(expectedOutputPerAllocation);
            output.setOutputPerRequest(outputRequest);

            // Add result
            AllocationResult result = new AllocationResult();
            result.setStageOutputId(output.getId());
            result.setFactoryStageId(factoryStageId);
            result.setStageName(node.getSmallStage().getStageName());
            result.setComponentId(output.getComponentId());
            result.setComponentName(output.getComponentName());
            result.setResultedAmount(expectedOutputPerAllocation);
            result.setFullAmount(requestedOutput);

            results.add(result);
        }
    }
}

package org.chainoptim.features.production.analysis.resourceallocation.service;

import org.chainoptim.features.production.analysis.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.production.analysis.resourceallocation.model.AllocationResult;
import org.chainoptim.features.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.production.factorygraph.model.StageNode;
import org.chainoptim.features.production.inventory.model.FactoryInventoryItem;

import java.util.List;
import java.util.Map;

public interface ResourceAllocatorService {
    AllocationPlan allocateResourcesFromInventory(FactoryGraph factoryGraph,
                                                  Map<Integer, FactoryInventoryItem> inventoryMap,
                                                  Float duration);

    void computeExpectedAndRequestedStageOutputs(StageNode node, Integer factoryStageId, float durationRatio, List<AllocationResult> results);
}

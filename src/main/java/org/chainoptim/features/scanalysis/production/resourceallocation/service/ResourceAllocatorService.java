package org.chainoptim.features.scanalysis.production.resourceallocation.service;

import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.factorygraph.model.StageNode;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.production.model.FactoryInventoryItem;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.AllocationResult;

import java.util.List;
import java.util.Map;

public interface ResourceAllocatorService {
    AllocationPlan allocateResourcesFromInventory(FactoryGraph factoryGraph,
                                     Map<Integer, FactoryInventoryItem> inventoryMap,
                                     Float duration);

    void computeExpectedAndRequestedStageOutputs(StageNode node, Integer factoryStageId, float durationRatio, List<AllocationResult> results);
}

package org.chainoptim.features.evaluation.production.resourceallocation.service;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.factory.model.FactoryInventoryItem;

import java.util.Map;

public interface ResourceAllocatorService {
    AllocationPlan allocateResourcesFromInventory(FactoryGraph factoryGraph,
                                     Map<Integer, FactoryInventoryItem> inventoryMap,
                                     Float duration);
}

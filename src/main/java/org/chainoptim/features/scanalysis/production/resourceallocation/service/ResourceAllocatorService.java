package org.chainoptim.features.scanalysis.production.resourceallocation.service;

import org.chainoptim.features.scanalysis.production.graph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.factory.model.FactoryInventoryItem;

import java.util.Map;

public interface ResourceAllocatorService {
    AllocationPlan allocateResourcesFromInventory(FactoryGraph factoryGraph,
                                     Map<Integer, FactoryInventoryItem> inventoryMap,
                                     Float duration);
}

package org.chainoptim.features.scanalysis.production.resourceallocation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.chainoptim.features.scanalysis.production.graph.model.FactoryGraph;
import org.chainoptim.features.factory.model.FactoryInventoryItem;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class AllocationPlan {

    private FactoryGraph factoryGraph;

    private Map<Integer, FactoryInventoryItem> inventoryBalance; // Key: inventoryItemId, Value: corresponding item after allocation

    private List<ResourceAllocation> allocations;
}

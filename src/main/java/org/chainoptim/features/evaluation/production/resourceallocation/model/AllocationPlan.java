package org.chainoptim.features.evaluation.production.resourceallocation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.resourceallocation.model.ResourceAllocation;
import org.chainoptim.features.factory.model.FactoryInventoryItem;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class AllocationPlan {

    private FactoryGraph factoryGraph;

    private Map<Integer, FactoryInventoryItem> inventoryBalance;

    private List<ResourceAllocation> allocationDeficit;
}

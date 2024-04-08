package org.chainoptim.features.scanalysis.production.productionhistory.model;

import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyProductionRecord {
    private List<ResourceAllocation> plannedResourceAllocations;
    private List<ResourceAllocation> actualResourceAllocations;
}

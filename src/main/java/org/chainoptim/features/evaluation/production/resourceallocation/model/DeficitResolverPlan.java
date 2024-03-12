package org.chainoptim.features.evaluation.production.resourceallocation.model;

import lombok.Data;
import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;

import java.util.List;

@Data
public class DeficitResolverPlan {
    private FactoryGraph factoryGraph;

    private List<DeficitResolution> resolutions;

    private List<ResourceAllocation> allocationDeficits;

}

package org.chainoptim.features.scanalysis.production.resourceallocation.model;

import lombok.Data;
import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryGraph;

import java.util.List;

@Data
public class DeficitResolverPlan {
    private FactoryGraph factoryGraph;

    private List<ResourceAllocation> allocationDeficits;

    private List<DeficitResolution> resolutions;

}

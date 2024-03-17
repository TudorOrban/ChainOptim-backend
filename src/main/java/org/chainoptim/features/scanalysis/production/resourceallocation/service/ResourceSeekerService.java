package org.chainoptim.features.scanalysis.production.resourceallocation.service;

import org.chainoptim.features.scanalysis.production.resourceallocation.model.DeficitResolution;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.DeficitResolverPlan;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocation;
import org.chainoptim.shared.commonfeatures.location.model.Location;

import java.util.List;

public interface ResourceSeekerService {

    DeficitResolverPlan seekResources(Integer organizationId, List<ResourceAllocation> allocationDeficits, Location factoryLocation);
    void seekWarehouseResources(Integer organizationId,
                                List<ResourceAllocation> allocationDeficits,
                                Location factoryLocation,
                                List<DeficitResolution> resolutions);
}

package org.chainoptim.features.evaluation.production.resourceallocation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.ResourceAllocation;
import org.chainoptim.shared.commonfeatures.location.model.Location;

import java.util.List;

public interface ResourceSeekerService {

    void seekResources(Integer organizationId, List<ResourceAllocation> allocationDeficits, Location factoryLocation);
}

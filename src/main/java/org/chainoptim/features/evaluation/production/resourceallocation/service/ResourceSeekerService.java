package org.chainoptim.features.evaluation.production.resourceallocation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.ResourceAllocation;

import java.util.List;

public interface ResourceSeekerService {

    void seekResources(Integer organizationId, List<ResourceAllocation> allocationDeficits);
}

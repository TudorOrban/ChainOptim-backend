package org.chainoptim.features.production.analysis.resourceallocation.service;

import org.chainoptim.features.production.analysis.resourceallocation.dto.CreateAllocationPlanDTO;
import org.chainoptim.features.production.analysis.resourceallocation.dto.UpdateAllocationPlanDTO;
import org.chainoptim.features.production.analysis.resourceallocation.model.ResourceAllocationPlan;

public interface ResourceAllocationPlanPersistenceService {

    ResourceAllocationPlan getResourceAllocationPlan(Integer factoryId);
    ResourceAllocationPlan createResourceAllocationPlan(CreateAllocationPlanDTO planDTO);
    ResourceAllocationPlan updateResourceAllocationPlan(UpdateAllocationPlanDTO planDTO);
    void deleteResourceAllocationPlan(Integer id);
}

package org.chainoptim.features.scanalysis.production.resourceallocation.service;

import org.chainoptim.features.scanalysis.production.resourceallocation.dto.CreateAllocationPlanDTO;
import org.chainoptim.features.scanalysis.production.resourceallocation.dto.UpdateAllocationPlanDTO;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocationPlan;

public interface ResourceAllocationPlanPersistenceService {

    ResourceAllocationPlan getResourceAllocationPlan(Integer factoryId);
    ResourceAllocationPlan createResourceAllocationPlan(CreateAllocationPlanDTO planDTO);
    ResourceAllocationPlan updateResourceAllocationPlan(UpdateAllocationPlanDTO planDTO);
    void deleteResourceAllocationPlan(Integer id);
}

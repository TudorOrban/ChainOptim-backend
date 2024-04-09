package org.chainoptim.features.scanalysis.production.resourceallocation.dto;

import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocationPlan;

public class PlanDTOMapper {

    public static ResourceAllocationPlan mapCreatePlanDTOToPlan(CreateAllocationPlanDTO createPlanDTO) {
        ResourceAllocationPlan plan = new ResourceAllocationPlan();
        plan.setFactoryId(createPlanDTO.getFactoryId());
        plan.setAllocationPlan(createPlanDTO.getAllocationPlan());

        return plan;
    }

    public static void setUpdatePlanDTOToPlan(UpdateAllocationPlanDTO updatePlanDTO, ResourceAllocationPlan plan) {
        plan.setFactoryId(updatePlanDTO.getFactoryId());
        plan.setAllocationPlan(updatePlanDTO.getAllocationPlan());
    }
}

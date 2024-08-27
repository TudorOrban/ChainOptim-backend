package org.chainoptim.features.production.analysis.resourceallocation.dto;

import org.chainoptim.features.production.analysis.resourceallocation.model.ResourceAllocationPlan;

public class PlanDTOMapper {

    public static ResourceAllocationPlan mapCreatePlanDTOToPlan(CreateAllocationPlanDTO createPlanDTO) {
        ResourceAllocationPlan plan = new ResourceAllocationPlan();
        plan.setFactoryId(createPlanDTO.getFactoryId());
        plan.setAllocationPlan(createPlanDTO.getAllocationPlan());
        plan.setActivationDate(createPlanDTO.getActivationDate());
        plan.setIsActive(true);

        return plan;
    }

    public static void setUpdatePlanDTOToPlan(UpdateAllocationPlanDTO updatePlanDTO, ResourceAllocationPlan plan) {
        plan.setFactoryId(updatePlanDTO.getFactoryId());
        plan.setAllocationPlan(updatePlanDTO.getAllocationPlan());
        plan.setActivationDate(updatePlanDTO.getActivationDate());
        plan.setIsActive(updatePlanDTO.isActive());
    }
}

package org.chainoptim.core.tenant.subscription.dto;

import org.chainoptim.core.tenant.subscription.model.SubscriptionPlan;

public class SubscriptionPlanDTOMapper {

    private SubscriptionPlanDTOMapper() {}

    public static SubscriptionPlan mapCreateSubscriptionPlanDTOToSubscriptionPlan(CreateSubscriptionPlanDTO planDTO) {
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setOrganizationId(planDTO.getOrganizationId());
        plan.setCustomPlan(planDTO.getCustomPlan());
        plan.setIsBasic(planDTO.getIsBasic());
        plan.setIsActive(false);
        plan.setIsPaid(false);
        return plan;
    }

    public static SubscriptionPlan setUpdateSubscriptionPlanDTOToSubscriptionPlan(UpdateSubscriptionPlanDTO planDTO, SubscriptionPlan plan) {
        plan.setOrganizationId(planDTO.getOrganizationId());
        plan.setCustomPlan(planDTO.getCustomPlan());
        return plan;
    }
}

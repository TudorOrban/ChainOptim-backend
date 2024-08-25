package org.chainoptim.core.subscription.service;

import org.chainoptim.core.subscription.dto.CreateSubscriptionPlanDTO;
import org.chainoptim.core.subscription.dto.UpdateSubscriptionPlanDTO;
import org.chainoptim.core.subscription.model.SubscriptionPlan;

public interface SubscriptionPlanService {

    SubscriptionPlan getSubscriptionPlanByOrganizationId(Integer organizationId);
    SubscriptionPlan createSubscriptionPlan(CreateSubscriptionPlanDTO planDTO);
    SubscriptionPlan updateSubscriptionPlan(UpdateSubscriptionPlanDTO planDTO);
    void deleteSubscriptionPlan(Integer id);
}

package org.chainoptim.core.tenant.subscription.service;

import org.chainoptim.core.tenant.subscription.dto.CreateSubscriptionPlanDTO;
import org.chainoptim.core.tenant.subscription.dto.UpdateSubscriptionPlanDTO;
import org.chainoptim.core.tenant.subscription.model.SubscriptionPlan;

public interface SubscriptionPlanService {

    SubscriptionPlan getSubscriptionPlanByOrganizationId(Integer organizationId);
    SubscriptionPlan createSubscriptionPlan(CreateSubscriptionPlanDTO planDTO);
    SubscriptionPlan updateSubscriptionPlan(UpdateSubscriptionPlanDTO planDTO);
    void deleteSubscriptionPlan(Integer id);
}

package org.chainoptim.core.tenant.subscription.service;

import org.chainoptim.shared.enums.Feature;

public interface SubscriptionPlanLimiterService {

    boolean isLimitReached(Integer organizationId, Feature feature, Integer quantity);
}

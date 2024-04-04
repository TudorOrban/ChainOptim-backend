package org.chainoptim.core.subscriptionplan.service;

import org.chainoptim.core.organization.model.Organization;

public interface SubscriptionPlanLimiterService {

    boolean isLimitReached(Integer organizationId, String featureName, Integer quantity);
}

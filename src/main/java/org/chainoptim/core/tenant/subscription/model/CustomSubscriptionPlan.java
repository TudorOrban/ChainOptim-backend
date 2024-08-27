package org.chainoptim.core.tenant.subscription.model;

import org.chainoptim.core.tenant.organization.model.SubscriptionPlanTier;
import org.chainoptim.shared.enums.Feature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomSubscriptionPlan {

    private SubscriptionPlanTier planTier;
    private Boolean isMonthly;
    private Float totalDollarsMonthly;
    private Map<Feature, Long> additionalFeatures;
}

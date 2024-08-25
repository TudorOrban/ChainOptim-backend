package org.chainoptim.core.subscription.model;

import org.chainoptim.core.organization.model.SubscriptionPlanTier;
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
    private Float totalDollarsMonthly;
    private Map<Feature, Float> additionalFeatures;
}

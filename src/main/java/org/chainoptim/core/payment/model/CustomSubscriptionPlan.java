package org.chainoptim.core.payment.model;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.shared.enums.Feature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomSubscriptionPlan {

    private Organization.SubscriptionPlanTier planTier;
    private Float totalDollarsMonthly;
    private Map<Feature, Float> additionalFeatures;
}

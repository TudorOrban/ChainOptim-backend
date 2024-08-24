package org.chainoptim.core.subscription.dto;

import org.chainoptim.core.subscription.model.CustomSubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubscriptionPlanDTO {
    private Integer organizationId;
    private CustomSubscriptionPlan customPlan;
    private Boolean isBasic;
}

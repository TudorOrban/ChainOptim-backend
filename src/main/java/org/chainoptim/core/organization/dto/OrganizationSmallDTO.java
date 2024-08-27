package org.chainoptim.core.organization.dto;

import org.chainoptim.core.organization.model.SubscriptionPlanTier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationSmallDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private SubscriptionPlanTier subscriptionPlanTier;
    private Boolean isPlanBasic;
}

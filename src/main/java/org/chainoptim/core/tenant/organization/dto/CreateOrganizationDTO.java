package org.chainoptim.core.tenant.organization.dto;

import org.chainoptim.core.tenant.organization.model.SubscriptionPlanTier;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateOrganizationDTO {
    private String name;
    private String address;
    private String contactInfo;
    private String creatorId;
    private Set<CreateOrganizationUserDTO> createdUsers;
    private Set<String> existingUserIds;
    private SubscriptionPlanTier planTier;
}

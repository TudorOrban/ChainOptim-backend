package com.TudorAOrban.chainoptimizer.dto;

import com.TudorAOrban.chainoptimizer.organization.model.Organization;
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
    private Organization.SubscriptionPlan subscriptionPlan;
}

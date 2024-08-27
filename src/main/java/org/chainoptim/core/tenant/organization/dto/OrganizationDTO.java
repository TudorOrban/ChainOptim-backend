package org.chainoptim.core.tenant.organization.dto;


import org.chainoptim.core.tenant.organization.model.SubscriptionPlanTier;
import org.chainoptim.core.tenant.user.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrganizationDTO {
    private Integer id;
    private String name;
    private String address;
    private String contactInfo;
    private SubscriptionPlanTier subscriptionPlanTier;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
    private Set<UserDTO> users;
}


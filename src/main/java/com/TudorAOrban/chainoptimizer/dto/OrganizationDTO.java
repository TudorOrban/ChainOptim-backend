package com.TudorAOrban.chainoptimizer.dto;


import com.TudorAOrban.chainoptimizer.organization.model.Organization;
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
    private Organization.SubscriptionPlan subscriptionPlan;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
    private Set<UserDTO> users;
}
package org.chainoptim.core.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateOrganizationInviteDTO {
    private Integer organizationId;
    private String inviterId;
    private String inviteeId;
}


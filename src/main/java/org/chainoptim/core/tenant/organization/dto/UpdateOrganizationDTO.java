package org.chainoptim.core.tenant.organization.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrganizationDTO {

    private Integer id;
    private String name;
    private String address;
    private String contactInfo;
}

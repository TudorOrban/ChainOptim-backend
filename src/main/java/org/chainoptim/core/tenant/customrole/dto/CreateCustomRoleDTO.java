package org.chainoptim.core.tenant.customrole.dto;

import org.chainoptim.core.tenant.customrole.model.Permissions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomRoleDTO {

    private String name;
    private Integer organizationId;
    private Permissions permissions;
}

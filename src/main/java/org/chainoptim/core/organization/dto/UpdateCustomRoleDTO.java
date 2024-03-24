package org.chainoptim.core.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.core.organization.model.Permissions;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomRoleDTO {

    private Integer id;
    private String name;
    private Permissions permissions;
}

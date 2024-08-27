package org.chainoptim.core.tenant.customrole.dto;

import org.chainoptim.core.tenant.customrole.model.CustomRole;

public class CustomRoleDTOMapper {

    public static CustomRole mapCreateCustomRoleDTOToCustomRole(CreateCustomRoleDTO roleDTO) {
        CustomRole role = new CustomRole();
        role.setName(roleDTO.getName());
        role.setOrganizationId(roleDTO.getOrganizationId());
        role.setPermissions(roleDTO.getPermissions());
        return role;
    }

    public static CustomRole setUpdateCustomRoleDTOToCustomRole(UpdateCustomRoleDTO roleDTO, CustomRole customRole) {
        customRole.setName(roleDTO.getName());
        customRole.setPermissions(roleDTO.getPermissions());
        return customRole;
    }
}

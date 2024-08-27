package org.chainoptim.core.tenant.customrole.service;

import org.chainoptim.core.tenant.customrole.dto.CreateCustomRoleDTO;
import org.chainoptim.core.tenant.customrole.dto.UpdateCustomRoleDTO;
import org.chainoptim.core.tenant.customrole.model.CustomRole;

import java.util.List;

public interface CustomRoleService {
    List<CustomRole> getCustomRolesByOrganizationId(Integer organizationId);
    CustomRole createCustomRole(CreateCustomRoleDTO roleDTO);
    CustomRole updateCustomRole(Integer roleId, UpdateCustomRoleDTO roleDTO);
    void deleteCustomRole(Integer roleId);
}

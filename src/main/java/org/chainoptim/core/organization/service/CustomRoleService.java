package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateCustomRoleDTO;
import org.chainoptim.core.organization.dto.UpdateCustomRoleDTO;
import org.chainoptim.core.organization.model.CustomRole;

import java.util.List;

public interface CustomRoleService {
    List<CustomRole> getCustomRolesByOrganizationId(Integer organizationId);
    CustomRole createCustomRole(CreateCustomRoleDTO roleDTO);
    CustomRole updateCustomRole(Integer roleId, UpdateCustomRoleDTO roleDTO);
    void deleteCustomRole(Integer roleId);
}

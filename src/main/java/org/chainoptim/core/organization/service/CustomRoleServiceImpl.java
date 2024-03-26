package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateCustomRoleDTO;
import org.chainoptim.core.organization.dto.CustomRoleDTOMapper;
import org.chainoptim.core.organization.dto.UpdateCustomRoleDTO;
import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.organization.repository.CustomRoleRepository;
import org.chainoptim.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomRoleServiceImpl implements CustomRoleService {

    private final CustomRoleRepository customRoleRepository;

    @Autowired
    public CustomRoleServiceImpl(CustomRoleRepository customRoleRepository) {
        this.customRoleRepository = customRoleRepository;
    }

    // Fetch
    public List<CustomRole> getCustomRolesByOrganizationId(Integer organizationId) {
        return customRoleRepository.findByOrganizationId(organizationId);
    }

    // Create
    public CustomRole createCustomRole(CreateCustomRoleDTO roleDTO) {
        return customRoleRepository.save(CustomRoleDTOMapper.mapCreateCustomRoleDTOToCustomRole(roleDTO));
    }

    // Update
    public CustomRole updateCustomRole(Integer roleId, UpdateCustomRoleDTO roleDTO) {
        CustomRole customRole = customRoleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Custom role with ID: " + roleId + " not found."));

        return customRoleRepository.save(CustomRoleDTOMapper.setUpdateCustomRoleDTOToCustomRole(roleDTO, customRole));
    }

    // Delete
    public void deleteCustomRole(Integer roleId) {
        customRoleRepository.deleteById(roleId);
    }
}

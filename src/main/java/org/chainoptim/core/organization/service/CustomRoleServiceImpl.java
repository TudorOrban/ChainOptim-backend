package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateCustomRoleDTO;
import org.chainoptim.core.organization.dto.CustomRoleDTOMapper;
import org.chainoptim.core.organization.dto.UpdateCustomRoleDTO;
import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.organization.repository.CustomRoleRepository;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.enums.Feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomRoleServiceImpl implements CustomRoleService {

    private final CustomRoleRepository customRoleRepository;
    private final SubscriptionPlanLimiterService planLimiterService;

    @Autowired
    public CustomRoleServiceImpl(CustomRoleRepository customRoleRepository,
                                 SubscriptionPlanLimiterService planLimiterService) {
        this.customRoleRepository = customRoleRepository;
        this.planLimiterService = planLimiterService;
    }

    // Fetch
    public List<CustomRole> getCustomRolesByOrganizationId(Integer organizationId) {
        return customRoleRepository.findByOrganizationId(organizationId);
    }

    // Create
    public CustomRole createCustomRole(CreateCustomRoleDTO roleDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(roleDTO.getOrganizationId(), Feature.CUSTOM_ROLE, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed custom roles for the current Subscription Plan.");
        }

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

package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.OrganizationDTO;
import org.chainoptim.core.user.dto.UserDTO;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.user.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrganizationMapper {

    public OrganizationDTO mapOrganizationToDTO(Organization organization, boolean includeUsers) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setAddress(organization.getAddress());
        dto.setContactInfo(organization.getContactInfo());
        dto.setSubscriptionPlan(organization.getSubscriptionPlan());
        dto.setCreatedAt(organization.getCreatedAt());
        dto.setUpdatedAt(organization.getUpdatedAt());

        if (includeUsers) {
            Set<UserDTO> userDTOs = organization.getUsers().stream()
                    .map(this::mapUserToDTO)
                    .collect(Collectors.toSet());
            dto.setUsers(userDTOs);
        }
        return dto;
    }

    public UserDTO mapUserToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setRole(user.getRole());
        return dto;
    }
}
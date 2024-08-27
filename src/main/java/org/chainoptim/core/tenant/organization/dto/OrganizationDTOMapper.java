package org.chainoptim.core.tenant.organization.dto;

import org.chainoptim.core.tenant.user.dto.UserDTO;
import org.chainoptim.core.tenant.organization.model.Organization;
import org.chainoptim.core.tenant.user.model.User;
import org.hibernate.Hibernate;

import java.util.Set;
import java.util.stream.Collectors;

public class OrganizationDTOMapper {

    private OrganizationDTOMapper() {}


    public static OrganizationDTO mapOrganizationToDTO(Organization organization, boolean includeUsers) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setAddress(organization.getAddress());
        dto.setContactInfo(organization.getContactInfo());
        dto.setSubscriptionPlanTier(organization.getSubscriptionPlanTier());
        dto.setCreatedAt(organization.getCreatedAt());
        dto.setUpdatedAt(organization.getUpdatedAt());

        if (includeUsers) {
            Set<UserDTO> userDTOs = organization.getUsers().stream()
                    .map(OrganizationDTOMapper::mapUserToDTO)
                    .collect(Collectors.toSet());
            dto.setUsers(userDTOs);
        }
        return dto;
    }

    public static UserDTO mapUserToDTO(User user) {
        UserDTO dto = new UserDTO();
        Hibernate.initialize(user.getCustomRole());
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setRole(user.getRole());
        dto.setCustomRole(user.getCustomRole());
        return dto;
    }
}

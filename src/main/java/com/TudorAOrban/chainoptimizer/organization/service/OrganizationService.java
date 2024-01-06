package com.TudorAOrban.chainoptimizer.organization.service;

import com.TudorAOrban.chainoptimizer.dto.CreateOrganizationDTO;
import com.TudorAOrban.chainoptimizer.dto.CreateOrganizationInviteDTO;
import com.TudorAOrban.chainoptimizer.dto.OrganizationDTO;
import com.TudorAOrban.chainoptimizer.organization.model.Organization;
import com.TudorAOrban.chainoptimizer.organization.model.OrganizationInvite;
import com.TudorAOrban.chainoptimizer.organization.repository.OrganizationRepository;
import com.TudorAOrban.chainoptimizer.user.model.User;
import com.TudorAOrban.chainoptimizer.user.repository.UserRepository;
import com.TudorAOrban.chainoptimizer.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationInviteService organizationInviteService;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Transactional
    public Organization createOrganization(CreateOrganizationDTO createOrganizationDTO) {
        Organization organization = new Organization();
        organization.setName(createOrganizationDTO.getName());
        organization.setAddress(createOrganizationDTO.getAddress());
        organization.setContactInfo(createOrganizationDTO.getContactInfo());
        organization.setSubscriptionPlan(createOrganizationDTO.getSubscriptionPlan());

        // Save the organization
        Organization savedOrganization = organizationRepository.save(organization);

        // Handle users
        // - For new ones, use User service to register and tie to organization
        Set<User> newUsers = createOrganizationDTO.getCreatedUsers().stream()
                .map(createDto -> userService.registerNewOrganizationUser(createDto.getUsername(), createDto.getPassword(), createDto.getEmail(), savedOrganization.getId(), createDto.getRole()))
                .collect(Collectors.toSet());

        // - For existing users, only send invites
        if (createOrganizationDTO.getExistingUserIds() != null) {
            createOrganizationDTO.getExistingUserIds().forEach(userId -> {
                User existingUser = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                CreateOrganizationInviteDTO inviteDTO = new CreateOrganizationInviteDTO(
                        savedOrganization.getId(), createOrganizationDTO.getCreatorId(), existingUser.getId());
                organizationInviteService.createOrganizationInvite(inviteDTO);
            });
        }

        // - For creator, set role to admin and tie to organization
        User creator = userRepository.findById(createOrganizationDTO.getCreatorId())
                .orElseThrow(() -> new IllegalArgumentException("Creator not found"));
        creator.setRole(User.Role.ADMIN);
        creator.setOrganization(savedOrganization);
        userRepository.save(creator);

        return savedOrganization;
    }


    @Transactional
    public OrganizationDTO getOrganizationById(Integer id, boolean includeUsers) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization != null) {
            return organizationMapper.mapOrganizationToDTO(organization, includeUsers);
        }
        return null;
    }



    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization updateOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Integer id) {
        organizationRepository.deleteById(id);
    }
}

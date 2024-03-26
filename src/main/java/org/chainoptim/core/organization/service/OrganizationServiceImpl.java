package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.CreateOrganizationUserDTO;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.organization.dto.CreateOrganizationDTO;
import org.chainoptim.core.organization.dto.CreateOrganizationInviteDTO;
import org.chainoptim.core.organization.dto.OrganizationDTO;
import org.chainoptim.core.organization.util.OrganizationMapper;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.core.user.service.UserService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final OrganizationInviteService organizationInviteService;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   UserRepository userRepository,
                                   UserService userService,
                                   OrganizationInviteService organizationInviteService
                                   ) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.organizationInviteService = organizationInviteService;
    }

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
        // - For new user, use User service to register and tie to organization
        for (CreateOrganizationUserDTO createDto: createOrganizationDTO.getCreatedUsers()) {
            userService.registerNewOrganizationUser(createDto.getUsername(), createDto.getPassword(), createDto.getEmail(), savedOrganization.getId(), createDto.getRole());
        }

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
            return OrganizationMapper.mapOrganizationToDTO(organization, includeUsers);
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

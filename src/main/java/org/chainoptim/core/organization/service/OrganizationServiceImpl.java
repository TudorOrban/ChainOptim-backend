package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.dto.*;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.model.SubscriptionPlanTier;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.core.user.service.UserWriteService;
import org.chainoptim.core.user.service.UserService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserWriteService userWriteService;
    private final OrganizationInviteService organizationInviteService;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   UserRepository userRepository,
                                   UserService userService,
                                   UserWriteService userWriteService,
                                   OrganizationInviteService organizationInviteService) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userWriteService = userWriteService;
        this.organizationInviteService = organizationInviteService;
    }

    @Transactional
    public OrganizationDTO getOrganizationById(Integer id, boolean includeUsers) {
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization != null) {
            return OrganizationDTOMapper.mapOrganizationToDTO(organization, includeUsers);
        }
        return null;
    }

    @Transactional
    public Organization createOrganization(CreateOrganizationDTO createOrganizationDTO) {
        // Create organization
        Organization organization = new Organization();
        organization.setName(createOrganizationDTO.getName());
        organization.setAddress(createOrganizationDTO.getAddress());
        organization.setContactInfo(createOrganizationDTO.getContactInfo());
        organization.setSubscriptionPlanTier(createOrganizationDTO.getPlanTier());

        // Ensure creation is within plan limits
        int existingUsers = createOrganizationDTO.getExistingUserIds() != null ? createOrganizationDTO.getExistingUserIds().size() : 0;
        int totalUsers = createOrganizationDTO.getCreatedUsers().size() + existingUsers + 1;
        int maxMembers = organization.getSubscriptionPlan().getMaxMembers();
        if (totalUsers > maxMembers && maxMembers != -1) {
            throw new PlanLimitReachedException("You are attempting to create more users than allowed by your current Subscription Plan.");
        }

        Organization savedOrganization = organizationRepository.save(organization);

        // Handle users
        // - For new user, use User service to register and tie to organization
        for (CreateOrganizationUserDTO createDTO: createOrganizationDTO.getCreatedUsers()) {
            userWriteService.registerNewOrganizationUser(createDTO.getUsername(), createDTO.getEmail(), savedOrganization.getId(), createDTO.getRole());
        }

        // - For existing users, only send invites
        if (createOrganizationDTO.getExistingUserIds() != null) {
            createOrganizationDTO.getExistingUserIds().forEach(userId -> {
                User existingUser = userService.getUserById(userId);
                CreateOrganizationInviteDTO inviteDTO = new CreateOrganizationInviteDTO(
                        savedOrganization.getId(), createOrganizationDTO.getCreatorId(), existingUser.getId());
                organizationInviteService.createOrganizationInvite(inviteDTO);
            });
        }

        // - For creator, set role to admin and tie to organization
        User creator = userService.getUserById(createOrganizationDTO.getCreatorId());
        creator.setRole(User.Role.ADMIN);
        creator.setOrganization(savedOrganization);
        userRepository.save(creator);

        return savedOrganization;
    }

    public Organization updateOrganization(UpdateOrganizationDTO organizationDTO) {
        Organization organization = organizationRepository.findById(organizationDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID: " + organizationDTO.getId() + " not found"));
        organization.setName(organizationDTO.getName());
        organization.setAddress(organizationDTO.getAddress());
        organization.setContactInfo(organizationDTO.getContactInfo());
        return organizationRepository.save(organization);
    }

    @Transactional
    public void deleteOrganization(Integer id) {
        Organization organization = organizationRepository.findByIdWithUsers(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID: " + id + " not found"));

        for (User user: organization.getUsers()) {
            user.setOrganization(null);
            userRepository.save(user);
        }

        organizationRepository.deleteById(id);
    }

    public void unsubscribeOrganization(Integer id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID: " + id + " not found"));
        organization.setIsPlanBasic(true);
        organization.setIsPlanActive(true);
        organization.setSubscriptionPlanTier(SubscriptionPlanTier.NONE);
        organizationRepository.save(organization);
    }
}

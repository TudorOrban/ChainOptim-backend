package org.chainoptim.config.security;

import org.chainoptim.core.organization.service.OrganizationIdFinderService;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.exception.AuthorizationException;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private final CustomRoleSecurityService customRoleSecurityService;

    private final UserRepository userRepository;
    private final OrganizationIdFinderService organizationIdFinderService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class.getName());

    @Autowired
    public SecurityServiceImpl(
            CustomRoleSecurityService customRoleSecurityService,
            UserRepository userRepository,
            OrganizationIdFinderService organizationIdFinderService
    ) {
        this.customRoleSecurityService = customRoleSecurityService;
        this.userRepository = userRepository;
        this.organizationIdFinderService = organizationIdFinderService;

    }

    public boolean canAccessEntity(Long entityId, String entityType, String operationType) {
        Optional<Integer> entityOrganizationId = organizationIdFinderService.findOrganizationIdByEntityId(entityId, entityType);

        return canAccessOrganizationEntity(entityOrganizationId, entityType, operationType);
    }

    @Transactional
    public boolean canAccessOrganizationEntity(Optional<Integer> organizationId, String entityType, String operationType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails;
        try {
            userDetails = (UserDetailsImpl) authentication.getPrincipal();
        } catch (Exception e) {
            throw new AuthorizationException("User not authenticated");
        }
        Integer currentOrganizationId = userDetails.getOrganizationId();

        // Condition 1: Belonging to the same organization
        boolean belongsToOrganization = organizationId.map(id -> id.equals(currentOrganizationId)).orElse(false);
        if (!belongsToOrganization) {
            logger.warn("User {} is attempting to access a resource belonging to: {}, not their own organization: {}", userDetails.getUsername(), organizationId.orElse(null), currentOrganizationId);
            return false;
        }

        // Condition 2: Check if user has permissions (with basic or custom role)
        boolean hasPermissions;
        if (userDetails.getCustomRole() == null) {
            logger.warn("User {} does not have a custom role", userDetails.getUsername());
            hasPermissions = canAccessOrganizationEntityWithBasicRole(userDetails.getRole(), operationType);
        } else {
            logger.info("User {} has a custom role", userDetails.getUsername());
            hasPermissions = customRoleSecurityService.canUserAccessOrganizationEntity(currentOrganizationId, userDetails, entityType, operationType);
        }
        if (!hasPermissions) {
            logger.warn("User {} does not have permission to perform operation: {} on entity: {}", userDetails.getUsername(), operationType, entityType);
        }
        return hasPermissions;
    }

    private boolean canAccessOrganizationEntityWithBasicRole(User.Role basicRole, String operationType) {
        if (User.Role.ADMIN.equals(basicRole)) {
            return true; // Allow access if user is an admin
        }
        if (User.Role.MEMBER.equals(basicRole) && operationType.equals("Read")) {
            return true; // Allow access if user is a member and operation is read
        }
        return false; // Reject access otherwise
    }

    public boolean canUserAccessOrganizationEntity(String userId, String operationType) {
        Optional<Integer> organizationId = userRepository.findOrganizationIdById(userId);

        return canAccessOrganizationEntity(organizationId, "User", operationType);
    }
}

package org.chainoptim.core.general.security.service;

import org.chainoptim.core.tenant.organization.service.OrganizationIdFinderService;
import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.model.UserDetailsImpl;
import org.chainoptim.core.tenant.user.repository.UserRepository;
import org.chainoptim.exception.AuthorizationException;
import org.chainoptim.shared.enums.Feature;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.chainoptim.shared.enums.Feature.*;

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
        Feature feature = parseEntityType(entityType);
        Optional<Integer> entityOrganizationId = organizationIdFinderService.findOrganizationIdByEntityId(entityId, feature);

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
            hasPermissions = canAccessOrganizationEntityWithBasicRole(userDetails.getRole(), operationType);
        } else {
            Feature feature = parseEntityType(entityType);
            hasPermissions = customRoleSecurityService.canUserAccessOrganizationEntity(userDetails, feature, operationType);
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

    // Util
    private Feature parseEntityType(String entityType) {
        return switch (entityType) {
            case "User" -> MEMBER;
            case "CustomRole" -> CUSTOM_ROLE;
            case "Product" -> PRODUCT;
            case "Stage" -> PRODUCT_STAGE;
            case "Component" -> COMPONENT;
            case "TransportRoute" -> TRANSPORT_ROUTE;
            case "Pricing" -> PRICING;
            case "Factory" -> FACTORY;
            case "FactoryStage" -> FACTORY_STAGE;
            case "FactoryInventory" -> FACTORY_INVENTORY;
            case "ResourceAllocationPlan" -> RESOURCE_ALLOCATION_PLAN;
            case "FactoryProductionHistory" -> FACTORY_PRODUCTION_HISTORY;
            case "FactoryPerformance" -> FACTORY_PERFORMANCE;
            case "Warehouse" -> WAREHOUSE;
            case "WarehouseInventory" -> WAREHOUSE_INVENTORY;
            case "Compartment" -> COMPARTMENT;
            case "Crate" -> CRATE;
            case "Supplier" -> SUPPLIER;
            case "SupplierOrder" -> SUPPLIER_ORDER;
            case "SupplierShipment" -> SUPPLIER_SHIPMENT;
            case "SupplierPerformance" -> SUPPLIER_PERFORMANCE;
            case "Client" -> CLIENT;
            case "ClientOrder" -> CLIENT_ORDER;
            case "ClientShipment" -> CLIENT_SHIPMENT;
            case "ClientEvaluation" -> CLIENT_EVALUATION;
            case "UpcomingEvent" -> UPCOMING_EVENT;
            case "Location" -> LOCATION;
            default -> throw new IllegalArgumentException("Invalid entity type: " + entityType);
        };
    }
}

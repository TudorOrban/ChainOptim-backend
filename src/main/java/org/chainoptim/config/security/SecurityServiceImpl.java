package org.chainoptim.config.security;

import jakarta.transaction.Transactional;
import org.chainoptim.core.organization.repository.CustomRoleRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.features.client.repository.ClientOrderRepository;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.product.repository.UnitOfMeasurementRepository;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private final CustomRoleSecurityService customRoleSecurityService;
    private final CustomRoleRepository customRoleRepository;

    private final ProductRepository productRepository;
    private final StageRepository stageRepository;
    private final ComponentRepository componentRepository;
    private final FactoryRepository factoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierOrderRepository supplierOrderRepository;
    private final ClientRepository clientRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final LocationRepository locationRepository;
    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    @Autowired
    public SecurityServiceImpl(
            CustomRoleSecurityService customRoleSecurityService,
            CustomRoleRepository customRoleRepository,
            ProductRepository productRepository,
            StageRepository stageRepository,
            FactoryRepository factoryRepository,
            WarehouseRepository warehouseRepository,
            SupplierRepository supplierRepository,
            SupplierOrderRepository supplierOrderRepository,
            ClientRepository clientRepository,
            ClientOrderRepository clientOrderRepository,
            LocationRepository locationRepository,
            UnitOfMeasurementRepository unitOfMeasurementRepository,
            ComponentRepository componentRepository
    ) {
        this.customRoleSecurityService = customRoleSecurityService;
        this.customRoleRepository = customRoleRepository;
        this.productRepository = productRepository;
        this.stageRepository = stageRepository;
        this.factoryRepository = factoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
        this.supplierOrderRepository = supplierOrderRepository;
        this.clientRepository = clientRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.locationRepository = locationRepository;
        this.unitOfMeasurementRepository = unitOfMeasurementRepository;
        this.componentRepository = componentRepository;

    }

    public boolean canAccessEntity(Long entityId, String entityType, String operationType) {
        Optional<Integer> entityOrganizationId = switch (entityType) {
            case "CustomRole" -> customRoleRepository.findOrganizationIdById(entityId);
            case "Product" -> productRepository.findOrganizationIdById(entityId);
            case "Stage" -> stageRepository.findOrganizationIdById(entityId);
            case "Component" -> componentRepository.findOrganizationIdById(entityId);
            case "Factory" -> factoryRepository.findOrganizationIdById(entityId);
            case "Warehouse" -> warehouseRepository.findOrganizationIdById(entityId);
            case "Supplier" -> supplierRepository.findOrganizationIdById(entityId);
            case "SupplierOrder" -> supplierOrderRepository.findOrganizationIdById(entityId);
            case "Client" -> clientRepository.findOrganizationIdById(entityId);
            case "ClientOrder" -> clientOrderRepository.findOrganizationIdById(entityId);
            case "Location" -> locationRepository.findOrganizationIdById(entityId);
            case "UnitOfMeasurement" -> unitOfMeasurementRepository.findOrganizationIdById(entityId);
            default -> throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        };

        return canAccessOrganizationEntity(entityOrganizationId, entityType, operationType);
    }

    @Transactional
    public boolean canAccessOrganizationEntity(Optional<Integer> organizationId, String entityType, String operationType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer currentOrganizationId = userDetails.getOrganizationId();

        boolean belongsToOrganization = organizationId.map(id -> id.equals(currentOrganizationId)).orElse(false);

        if (!belongsToOrganization) {
            return false;
        }

        if (userDetails.getCustomRole() == null) {
            return canAccessOrganizationEntityWithBasicRole(userDetails.getRole(), operationType);
        } else {
            return customRoleSecurityService.canUserAccessOrganizationEntity(currentOrganizationId, userDetails, entityType, operationType);
        }
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
}

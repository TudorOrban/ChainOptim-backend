package org.chainoptim.config.security;

import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.chainoptim.features.supply.repository.SupplierRepository;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private final ProductRepository productRepository;
    private final StageRepository stageRepository;
    private final FactoryRepository factoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SecurityServiceImpl(
            ProductRepository productRepository,
            StageRepository stageRepository,
            FactoryRepository factoryRepository,
            WarehouseRepository warehouseRepository,
            SupplierRepository supplierRepository
    ) {
        this.productRepository = productRepository;
        this.stageRepository = stageRepository;
        this.factoryRepository = factoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
    }

    public boolean canAccessEntity(Long entityId, String entityType) {
        Optional<Integer> entityOrganizationId = switch (entityType) {
            case "Product" -> productRepository.findOrganizationIdById(entityId);
            case "Factory" -> factoryRepository.findOrganizationIdById(entityId);
            case "Warehouse" -> warehouseRepository.findOrganizationIdById(entityId);
            case "Supplier" -> supplierRepository.findOrganizationIdById(entityId);
            case "Stage" -> stageRepository.findOrganizationIdById(entityId);
            default -> throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        };

        return canAccessOrganizationEntity(entityOrganizationId);
    }

    public boolean canAccessOrganizationEntity(Optional<Integer> organizationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer currentOrganizationId = userDetails.getOrganizationId();

        return organizationId.map(id -> id.equals(currentOrganizationId)).orElse(false);
    }
}

package org.chainoptim.config.security;

import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.product.repository.ProductRepository;
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
    private final FactoryRepository factoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SecurityServiceImpl(
            ProductRepository productRepository,
            FactoryRepository factoryRepository,
            WarehouseRepository warehouseRepository,
            SupplierRepository supplierRepository
    ) {
        this.productRepository = productRepository;
        this.factoryRepository = factoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
    }

    public boolean canAccessEntity(Long entityId, String entityType) {
        Optional<Integer> entityOrganizationId;

        switch (entityType) {
            case "Product":
                entityOrganizationId = productRepository.findOrganizationIdById(entityId);
                break;
            case "Factory":
                entityOrganizationId = factoryRepository.findOrganizationIdById(entityId);
                break;
            case "Warehouse":
                entityOrganizationId = warehouseRepository.findOrganizationIdById(entityId);
                break;
            case "Supplier":
                entityOrganizationId = supplierRepository.findOrganizationIdById(entityId);
                break;
            default:
                throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        }

        return canAccessOrganizationEntity(entityOrganizationId);
    }

    public boolean canAccessOrganizationEntity(Optional<Integer> organizationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer currentOrganizationId = userDetails.getOrganizationId();

        return organizationId.map(id -> id.equals(currentOrganizationId)).orElse(false);
    }
}

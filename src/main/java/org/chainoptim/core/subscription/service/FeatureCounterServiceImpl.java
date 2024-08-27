package org.chainoptim.core.subscription.service;

import org.chainoptim.config.security.CustomRoleSecurityService;
import org.chainoptim.core.organization.repository.CustomRoleRepository;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.features.client.repository.ClientOrderRepository;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.factory.repository.FactoryInventoryItemRepository;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.product.repository.UnitOfMeasurementRepository;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.features.warehouse.repository.CompartmentRepository;
import org.chainoptim.features.warehouse.repository.CrateRepository;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.enums.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureCounterServiceImpl implements FeatureCounterService {

    private final UserRepository userRepository;
    private final CustomRoleRepository customRoleRepository;
    private final ProductRepository productRepository;
    private final StageRepository stageRepository;
    private final ComponentRepository componentRepository;
    private final FactoryRepository factoryRepository;
    private final FactoryInventoryItemRepository factoryInventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final CompartmentRepository compartmentRepository;
    private final CrateRepository crateRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierOrderRepository supplierOrderRepository;
    private final ClientRepository clientRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final LocationRepository locationRepository;
    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    @Autowired
    public FeatureCounterServiceImpl(
            UserRepository userRepository,
            CustomRoleRepository customRoleRepository,
            ProductRepository productRepository,
            StageRepository stageRepository,
            FactoryRepository factoryRepository,
            FactoryInventoryItemRepository factoryInventoryRepository,
            WarehouseRepository warehouseRepository,
            CompartmentRepository compartmentRepository,
            CrateRepository crateRepository,
            SupplierRepository supplierRepository,
            SupplierOrderRepository supplierOrderRepository,
            ClientRepository clientRepository,
            ClientOrderRepository clientOrderRepository,
            LocationRepository locationRepository,
            UnitOfMeasurementRepository unitOfMeasurementRepository,
            ComponentRepository componentRepository
    ) {
        this.userRepository = userRepository;
        this.customRoleRepository = customRoleRepository;
        this.productRepository = productRepository;
        this.stageRepository = stageRepository;
        this.factoryRepository = factoryRepository;
        this.factoryInventoryRepository = factoryInventoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.compartmentRepository = compartmentRepository;
        this.crateRepository = crateRepository;
        this.supplierRepository = supplierRepository;
        this.supplierOrderRepository = supplierOrderRepository;
        this.clientRepository = clientRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.locationRepository = locationRepository;
        this.unitOfMeasurementRepository = unitOfMeasurementRepository;
        this.componentRepository = componentRepository;
    }

    public long getCountByFeature(Integer entityId, Feature feature) {
        return switch (feature) {
            case CUSTOM_ROLE -> customRoleRepository.countByOrganizationId(entityId);
            case PRODUCT -> productRepository.countByOrganizationId(entityId);
            case PRODUCT_STAGE -> stageRepository.countByOrganizationId(entityId);
            case COMPONENT -> componentRepository.countByOrganizationId(entityId);
            case FACTORY -> factoryRepository.countByOrganizationId(entityId);
            case FACTORY_INVENTORY -> factoryInventoryRepository.countByOrganizationId(entityId);
            case WAREHOUSE -> warehouseRepository.countByOrganizationId(entityId);
            case SUPPLIER -> supplierRepository.countByOrganizationId(entityId);
            case SUPPLIER_ORDER -> supplierOrderRepository.countByOrganizationId(entityId);
            case CLIENT -> clientRepository.countByOrganizationId(entityId);
            case CLIENT_ORDER -> clientOrderRepository.countByOrganizationId(entityId);
            default -> 0L;
        };
    }
}

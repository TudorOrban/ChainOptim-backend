package org.chainoptim.core.subscription.service;

import org.chainoptim.core.organization.repository.CustomRoleRepository;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.features.client.repository.ClientOrderRepository;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.client.repository.ClientShipmentRepository;
import org.chainoptim.features.factory.repository.FactoryInventoryItemRepository;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.product.repository.PricingRepository;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.product.repository.ResourceTransportRouteRepository;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.features.supplier.repository.SupplierShipmentRepository;
import org.chainoptim.features.warehouse.repository.CompartmentRepository;
import org.chainoptim.features.warehouse.repository.CrateRepository;
import org.chainoptim.features.warehouse.repository.WarehouseInventoryItemRepository;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.enums.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureCounterServiceImpl implements FeatureCounterService {

    private final UserRepository userRepository;
    private final CustomRoleRepository customRoleRepository;
    private final ProductRepository productRepository;
    private final StageRepository stageRepository;
    private final ComponentRepository componentRepository;
    private final ResourceTransportRouteRepository transportRouteRepository;
    private final PricingRepository pricingRepository;
    private final FactoryRepository factoryRepository;
    private final FactoryInventoryItemRepository factoryInventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseInventoryItemRepository warehouseInventoryRepository;
    private final CompartmentRepository compartmentRepository;
    private final CrateRepository crateRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierOrderRepository supplierOrderRepository;
    private final SupplierShipmentRepository supplierShipmentRepository;
    private final ClientRepository clientRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final ClientShipmentRepository clientShipmentRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public FeatureCounterServiceImpl(
            UserRepository userRepository,
            CustomRoleRepository customRoleRepository,
            ProductRepository productRepository,
            StageRepository stageRepository,
            ComponentRepository componentRepository,
            ResourceTransportRouteRepository transportRouteRepository,
            PricingRepository pricingRepository,
            FactoryRepository factoryRepository,
            FactoryInventoryItemRepository factoryInventoryRepository,
            WarehouseRepository warehouseRepository,
            WarehouseInventoryItemRepository warehouseInventoryRepository,
            CompartmentRepository compartmentRepository,
            CrateRepository crateRepository,
            SupplierRepository supplierRepository,
            SupplierOrderRepository supplierOrderRepository,
            SupplierShipmentRepository supplierShipmentRepository,
            ClientRepository clientRepository,
            ClientOrderRepository clientOrderRepository,
            ClientShipmentRepository clientShipmentRepository,
            LocationRepository locationRepository
    ) {
        this.userRepository = userRepository;
        this.customRoleRepository = customRoleRepository;
        this.productRepository = productRepository;
        this.stageRepository = stageRepository;
        this.componentRepository = componentRepository;
        this.transportRouteRepository = transportRouteRepository;
        this.pricingRepository = pricingRepository;
        this.factoryRepository = factoryRepository;
        this.factoryInventoryRepository = factoryInventoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseInventoryRepository = warehouseInventoryRepository;
        this.compartmentRepository = compartmentRepository;
        this.crateRepository = crateRepository;
        this.supplierRepository = supplierRepository;
        this.supplierOrderRepository = supplierOrderRepository;
        this.supplierShipmentRepository = supplierShipmentRepository;
        this.clientRepository = clientRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.clientShipmentRepository = clientShipmentRepository;
        this.locationRepository = locationRepository;
    }

    public long getCountByFeature(Integer entityId, Feature feature) {
        return switch (feature) {
            case MEMBER -> userRepository.countByOrganizationId(entityId);
            case CUSTOM_ROLE -> customRoleRepository.countByOrganizationId(entityId);
            case PRODUCT -> productRepository.countByOrganizationId(entityId);
            case PRODUCT_STAGE -> stageRepository.countByOrganizationId(entityId);
            case COMPONENT -> componentRepository.countByOrganizationId(entityId);
            case TRANSPORT_ROUTE -> transportRouteRepository.countByOrganizationId(entityId);
            case PRICING -> pricingRepository.countByOrganizationId(entityId);
            case FACTORY -> factoryRepository.countByOrganizationId(entityId);
            case FACTORY_INVENTORY -> factoryInventoryRepository.countByOrganizationId(entityId);
            case WAREHOUSE -> warehouseRepository.countByOrganizationId(entityId);
            case WAREHOUSE_INVENTORY -> warehouseInventoryRepository.countByOrganizationId(entityId);
            case COMPARTMENT -> compartmentRepository.countByOrganizationId(entityId);
            case CRATE -> crateRepository.countByOrganizationId(entityId);
            case SUPPLIER -> supplierRepository.countByOrganizationId(entityId);
            case SUPPLIER_ORDER -> supplierOrderRepository.countByOrganizationId(entityId);
            case SUPPLIER_SHIPMENT -> supplierShipmentRepository.countByOrganizationId(entityId);
            case CLIENT -> clientRepository.countByOrganizationId(entityId);
            case CLIENT_ORDER -> clientOrderRepository.countByOrganizationId(entityId);
            case CLIENT_SHIPMENT -> clientShipmentRepository.countByOrganizationId(entityId);
            case LOCATION -> locationRepository.countByOrganizationId(entityId);
            default -> 0L;
        };
    }
}

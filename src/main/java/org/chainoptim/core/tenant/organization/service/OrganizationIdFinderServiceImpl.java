package org.chainoptim.core.tenant.organization.service;

import org.chainoptim.core.tenant.customrole.repository.CustomRoleRepository;
import org.chainoptim.features.demand.clientorder.repository.ClientOrderRepository;
import org.chainoptim.features.demand.client.repository.ClientRepository;
import org.chainoptim.features.demand.clientshipment.repository.ClientShipmentRepository;
import org.chainoptim.features.production.inventory.repository.FactoryInventoryItemRepository;
import org.chainoptim.features.production.factory.repository.FactoryRepository;
import org.chainoptim.features.production.stage.repository.FactoryStageRepository;
import org.chainoptim.features.goods.pricing.repository.PricingRepository;
import org.chainoptim.features.goods.product.repository.ProductRepository;
import org.chainoptim.features.goods.transportroute.repository.ResourceTransportRouteRepository;
import org.chainoptim.features.goods.component.repository.ComponentRepository;
import org.chainoptim.features.goods.stage.repository.StageRepository;
import org.chainoptim.features.supply.repository.SupplierOrderRepository;
import org.chainoptim.features.supply.repository.SupplierRepository;
import org.chainoptim.features.supply.repository.SupplierShipmentRepository;
import org.chainoptim.features.storage.repository.CompartmentRepository;
import org.chainoptim.features.storage.repository.CrateRepository;
import org.chainoptim.features.storage.repository.WarehouseInventoryItemRepository;
import org.chainoptim.features.storage.repository.WarehouseRepository;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.enums.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationIdFinderServiceImpl implements OrganizationIdFinderService {


    private final CustomRoleRepository customRoleRepository;

    private final ProductRepository productRepository;
    private final StageRepository stageRepository;
    private final ComponentRepository componentRepository;
    private final ResourceTransportRouteRepository transportRouteRepository;
    private final PricingRepository pricingRepository;
    private final FactoryRepository factoryRepository;
    private final FactoryStageRepository factoryStageRepository;
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
    public OrganizationIdFinderServiceImpl(
            CustomRoleRepository customRoleRepository,
            ProductRepository productRepository,
            StageRepository stageRepository,
            ComponentRepository componentRepository,
            ResourceTransportRouteRepository transportRouteRepository,
            PricingRepository pricingRepository,
            FactoryRepository factoryRepository,
            FactoryStageRepository factoryStageRepository,
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
        this.customRoleRepository = customRoleRepository;
        this.productRepository = productRepository;
        this.stageRepository = stageRepository;
        this.componentRepository = componentRepository;
        this.transportRouteRepository = transportRouteRepository;
        this.pricingRepository = pricingRepository;
        this.factoryRepository = factoryRepository;
        this.factoryStageRepository = factoryStageRepository;
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

    public Optional<Integer> findOrganizationIdByEntityId(Long entityId, Feature feature) {
        return switch (feature) {
            case CUSTOM_ROLE -> customRoleRepository.findOrganizationIdById(entityId);
            case PRODUCT -> productRepository.findOrganizationIdById(entityId);
            case PRODUCT_STAGE -> stageRepository.findOrganizationIdById(entityId);
            case COMPONENT -> componentRepository.findOrganizationIdById(entityId);
            case TRANSPORT_ROUTE -> transportRouteRepository.findOrganizationIdById(entityId);
            case PRICING -> pricingRepository.findOrganizationIdById(entityId);
            case FACTORY -> factoryRepository.findOrganizationIdById(entityId);
            case FACTORY_STAGE -> factoryStageRepository.findOrganizationIdById(entityId);
            case FACTORY_INVENTORY -> factoryInventoryRepository.findOrganizationIdById(entityId);
            case WAREHOUSE -> warehouseRepository.findOrganizationIdById(entityId);
            case WAREHOUSE_INVENTORY -> warehouseInventoryRepository.findOrganizationIdById(entityId);
            case COMPARTMENT -> compartmentRepository.findOrganizationIdById(entityId);
            case CRATE -> crateRepository.findOrganizationIdById(entityId);
            case SUPPLIER -> supplierRepository.findOrganizationIdById(entityId);
            case SUPPLIER_ORDER -> supplierOrderRepository.findOrganizationIdById(entityId);
            case SUPPLIER_SHIPMENT -> supplierShipmentRepository.findOrganizationIdById(entityId);
            case CLIENT -> clientRepository.findOrganizationIdById(entityId);
            case CLIENT_ORDER -> clientOrderRepository.findOrganizationIdById(entityId);
            case CLIENT_SHIPMENT -> clientShipmentRepository.findOrganizationIdById(entityId);
            case LOCATION -> locationRepository.findOrganizationIdById(entityId);
            default -> throw new IllegalArgumentException("Unsupported feature: " + feature);
        };
    }
}

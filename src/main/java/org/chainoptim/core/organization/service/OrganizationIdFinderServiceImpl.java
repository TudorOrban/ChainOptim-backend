package org.chainoptim.core.organization.service;

import org.chainoptim.core.organization.repository.CustomRoleRepository;
import org.chainoptim.features.client.repository.ClientOrderRepository;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.client.repository.ClientShipmentRepository;
import org.chainoptim.features.factory.repository.FactoryInventoryItemRepository;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.factory.repository.FactoryStageRepository;
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

    public Optional<Integer> findOrganizationIdByEntityId(Long entityId, String entityType) {
        return switch (entityType) {
            case "CustomRole" -> customRoleRepository.findOrganizationIdById(entityId);
            case "Product" -> productRepository.findOrganizationIdById(entityId);
            case "Stage" -> stageRepository.findOrganizationIdById(entityId);
            case "Component" -> componentRepository.findOrganizationIdById(entityId);
            case "ResourceTransportRoute" -> transportRouteRepository.findOrganizationIdById(entityId);
            case "Pricing" -> pricingRepository.findOrganizationIdById(entityId);
            case "Factory" -> factoryRepository.findOrganizationIdById(entityId);
            case "FactoryStage" -> factoryStageRepository.findOrganizationIdById(entityId);
            case "FactoryInventoryItem" -> factoryInventoryRepository.findOrganizationIdById(entityId);
            case "Warehouse" -> warehouseRepository.findOrganizationIdById(entityId);
            case "WarehouseInventoryItem" -> warehouseInventoryRepository.findOrganizationIdById(entityId);
            case "Compartment" -> compartmentRepository.findOrganizationIdById(entityId);
            case "Crate" -> crateRepository.findOrganizationIdById(entityId);
            case "Supplier" -> supplierRepository.findOrganizationIdById(entityId);
            case "SupplierOrder" -> supplierOrderRepository.findOrganizationIdById(entityId);
            case "SupplierShipment" -> supplierShipmentRepository.findOrganizationIdById(entityId);
            case "Client" -> clientRepository.findOrganizationIdById(entityId);
            case "ClientOrder" -> clientOrderRepository.findOrganizationIdById(entityId);
            case "ClientShipment" -> clientShipmentRepository.findOrganizationIdById(entityId);
            case "Location" -> locationRepository.findOrganizationIdById(entityId);
            default -> throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        };
    }
}

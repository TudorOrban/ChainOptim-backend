package org.chainoptim.core.overview.scsnapshot.service;

import org.chainoptim.core.tenant.customrole.repository.CustomRoleRepository;
import org.chainoptim.core.overview.scsnapshot.model.Snapshot;
import org.chainoptim.core.tenant.user.repository.UserRepository;
import org.chainoptim.features.demand.repository.ClientOrderRepository;
import org.chainoptim.features.demand.repository.ClientRepository;
import org.chainoptim.features.demand.repository.ClientShipmentRepository;
import org.chainoptim.features.production.repository.FactoryInventoryItemRepository;
import org.chainoptim.features.production.repository.FactoryRepository;
import org.chainoptim.features.production.repository.FactoryStageRepository;
import org.chainoptim.features.goods.product.repository.ProductRepository;
import org.chainoptim.features.goods.component.repository.ComponentRepository;
import org.chainoptim.features.goods.stage.repository.StageRepository;
import org.chainoptim.features.supply.repository.SupplierOrderRepository;
import org.chainoptim.features.supply.repository.SupplierRepository;
import org.chainoptim.features.supply.repository.SupplierShipmentRepository;
import org.chainoptim.features.storage.repository.WarehouseInventoryItemRepository;
import org.chainoptim.features.storage.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotFinderServiceImpl implements SnapshotFinderService {

    private final UserRepository userRepository;
    private final CustomRoleRepository customRoleRepository;
    private final ProductRepository productRepository;
    private final StageRepository stageRepository;
    private final ComponentRepository componentRepository;
    private final FactoryRepository factoryRepository;
    private final FactoryInventoryItemRepository factoryInventoryRepository;
    private final FactoryStageRepository factoryStageRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseInventoryItemRepository warehouseInventoryRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierOrderRepository supplierOrderRepository;
    private final SupplierShipmentRepository supplierShipmentRepository;
    private final ClientRepository clientRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final ClientShipmentRepository clientShipmentRepository;


    @Autowired
    public SnapshotFinderServiceImpl(UserRepository userRepository,
                                     CustomRoleRepository customRoleRepository,
                                     ProductRepository productRepository,
                                     StageRepository stageRepository,
                                     ComponentRepository componentRepository,
                                     FactoryRepository factoryRepository,
                                     FactoryInventoryItemRepository factoryInventoryRepository,
                                     FactoryStageRepository factoryStageRepository,
                                     WarehouseRepository warehouseRepository,
                                     WarehouseInventoryItemRepository warehouseInventoryRepository,
                                     SupplierRepository supplierRepository,
                                     SupplierOrderRepository supplierOrderRepository,
                                     SupplierShipmentRepository supplierShipmentRepository,
                                     ClientRepository clientRepository,
                                     ClientOrderRepository clientOrderRepository,
                                     ClientShipmentRepository clientShipmentRepository) {
        this.userRepository = userRepository;
        this.customRoleRepository = customRoleRepository;
        this.productRepository = productRepository;
        this.stageRepository = stageRepository;
        this.componentRepository = componentRepository;
        this.factoryRepository = factoryRepository;
        this.factoryInventoryRepository = factoryInventoryRepository;
        this.factoryStageRepository = factoryStageRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseInventoryRepository = warehouseInventoryRepository;
        this.supplierRepository = supplierRepository;
        this.supplierOrderRepository = supplierOrderRepository;
        this.supplierShipmentRepository = supplierShipmentRepository;
        this.clientRepository = clientRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.clientShipmentRepository = clientShipmentRepository;
    }

    public Snapshot getSnapshotByOrganizationId(Integer organizationId) {
        long membersCount = userRepository.countByOrganizationId(organizationId);
        long customRolesCount = customRoleRepository.countByOrganizationId(organizationId);
        long productCount = productRepository.countByOrganizationId(organizationId);
        long productStageCount = stageRepository.countByOrganizationId(organizationId);
        long componentCount = componentRepository.countByOrganizationId(organizationId);
        long factoryCount = factoryRepository.countByOrganizationId(organizationId);
        long factoryInventoryCount = factoryInventoryRepository.countByOrganizationId(organizationId);
        long factoryStageCount = factoryStageRepository.countByOrganizationId(organizationId);
        long warehouseCount = warehouseRepository.countByOrganizationId(organizationId);
        long warehouseInventoryCount = warehouseInventoryRepository.countByOrganizationId(organizationId);
        long suppliersCount = supplierRepository.countByOrganizationId(organizationId);
        long supplierOrdersCount = supplierOrderRepository.countByOrganizationId(organizationId);
        long supplierShipmentsCount = supplierShipmentRepository.countByOrganizationId(organizationId);
        long clientsCount = clientRepository.countByOrganizationId(organizationId);
        long clientOrdersCount = clientOrderRepository.countByOrganizationId(organizationId);
        long clientShipmentsCount = clientShipmentRepository.countByOrganizationId(organizationId);

        return new Snapshot(
                membersCount, customRolesCount,
                productCount, componentCount, productStageCount,
                factoryCount, factoryInventoryCount, factoryStageCount,
                warehouseCount, warehouseInventoryCount,
                suppliersCount, supplierOrdersCount, supplierShipmentsCount,
                clientsCount, clientOrdersCount, clientShipmentsCount
        );
    }
}

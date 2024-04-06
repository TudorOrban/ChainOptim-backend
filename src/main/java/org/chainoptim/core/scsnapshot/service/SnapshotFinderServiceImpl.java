package org.chainoptim.core.scsnapshot.service;

import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.features.client.repository.ClientOrderRepository;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.factory.repository.FactoryInventoryRepository;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.features.warehouse.repository.WarehouseInventoryRepository;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotFinderServiceImpl implements SnapshotFinderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ComponentRepository componentRepository;
    private final FactoryRepository factoryRepository;
    private final FactoryInventoryRepository factoryInventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierOrderRepository supplierOrderRepository;
    private final ClientRepository clientRepository;
    private final ClientOrderRepository clientOrderRepository;


    @Autowired
    public SnapshotFinderServiceImpl(UserRepository userRepository,
                                     ProductRepository productRepository,
                                     ComponentRepository componentRepository,
                                     FactoryRepository factoryRepository,
                                     FactoryInventoryRepository factoryInventoryRepository,
                                     WarehouseRepository warehouseRepository,
                                     WarehouseInventoryRepository warehouseInventoryRepository,
                                     SupplierRepository supplierRepository,
                                     SupplierOrderRepository supplierOrderRepository,
                                     ClientRepository clientRepository,
                                     ClientOrderRepository clientOrderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.componentRepository = componentRepository;
        this.factoryRepository = factoryRepository;
        this.factoryInventoryRepository = factoryInventoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseInventoryRepository = warehouseInventoryRepository;
        this.supplierRepository = supplierRepository;
        this.supplierOrderRepository = supplierOrderRepository;
        this.clientRepository = clientRepository;
        this.clientOrderRepository = clientOrderRepository;
    }

    public Snapshot getSnapshotByOrganizationId(Integer organizationId) {
        long membersCount = userRepository.countByOrganizationId(organizationId);
        long productCount = productRepository.countByOrganizationId(organizationId);
        long componentCount = componentRepository.countByOrganizationId(organizationId);
        long factoryCount = factoryRepository.countByOrganizationId(organizationId);
        long factoryInventoryCount = factoryInventoryRepository.findCountByOrganizationId(organizationId);
        long warehouseCount = warehouseRepository.countByOrganizationId(organizationId);
        long warehouseInventoryCount = warehouseInventoryRepository.findCountByOrganizationId(organizationId);
        long suppliersCount = supplierRepository.countByOrganizationId(organizationId);
        long supplierOrdersCount = supplierOrderRepository.findCountByOrganizationId(organizationId);
        long clientsCount = clientRepository.countByOrganizationId(organizationId);
        long clientOrdersCount = clientOrderRepository.findCountByOrganizationId(organizationId);

        Snapshot snapshot= new Snapshot(membersCount,
                productCount, componentCount,
                factoryCount, factoryInventoryCount,
                warehouseCount, warehouseInventoryCount,
                suppliersCount, supplierOrdersCount,
                clientsCount, clientOrdersCount);
        System.out.println(snapshot);
        return snapshot;
    }
}

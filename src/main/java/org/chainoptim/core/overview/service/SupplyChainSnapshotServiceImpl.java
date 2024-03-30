package org.chainoptim.core.overview.service;

import org.chainoptim.core.overview.model.SupplyChainSnapshot;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplyChainSnapshotServiceImpl implements SupplyChainSnapshotService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FactoryRepository factoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final ClientRepository clientRepository;


    @Autowired
    public SupplyChainSnapshotServiceImpl(UserRepository userRepository,
                                          ProductRepository productRepository,
                                          FactoryRepository factoryRepository,
                                          WarehouseRepository warehouseRepository,
                                          SupplierRepository supplierRepository,
                                          ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.factoryRepository = factoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
    }

    public SupplyChainSnapshot getSupplyChainSnapshot(Integer organizationId) {
        long membersCount = userRepository.countByOrganizationId(organizationId);
        long productCount = productRepository.countByOrganizationId(organizationId);
        long factoryCount = factoryRepository.countByOrganizationId(organizationId);
        long warehouseCount = warehouseRepository.countByOrganizationId(organizationId);
        long suppliersCount = supplierRepository.countByOrganizationId(organizationId);
        long clientsCount = clientRepository.countByOrganizationId(organizationId);

        return new SupplyChainSnapshot(membersCount, productCount, factoryCount, warehouseCount, suppliersCount, clientsCount);
    }
}

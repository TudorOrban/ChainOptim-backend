package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.SupplierOrder;
import org.chainoptim.features.supply.repository.SupplierOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;

    @Autowired
    public SupplierOrderServiceImpl(SupplierOrderRepository supplierOrderRepository) {
        this.supplierOrderRepository = supplierOrderRepository;
    }

    public List<SupplierOrder> getSupplierOrdersByOrganizationId(Integer organizationId) {
        return supplierOrderRepository.findByOrganizationId(organizationId);
    }

    public List<SupplierOrder> getSupplierOrdersBySupplierId(Integer supplierId) {
        return supplierOrderRepository.findBySupplierId(supplierId);
    }
}

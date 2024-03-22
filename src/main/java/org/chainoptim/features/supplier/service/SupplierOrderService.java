package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.model.SupplierOrder;

import java.util.List;

public interface SupplierOrderService {

    List<SupplierOrder> getSupplierOrdersByOrganizationId(Integer organizationId);
    List<SupplierOrder> getSupplierOrdersBySupplierId(Integer supplierId);

    SupplierOrder saveOrUpdateSupplierOrder(CreateSupplierOrderDTO order);
}

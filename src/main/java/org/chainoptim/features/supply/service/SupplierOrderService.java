package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.SupplierOrder;

import java.util.List;

public interface SupplierOrderService {

    List<SupplierOrder> getSupplierOrdersByOrganizationId(Integer organizationId);
    List<SupplierOrder> getSupplierOrdersBySupplierId(Integer supplierId);
}

package org.chainoptim.features.evaluation.production.resourceallocation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.ResourceAllocation;
import org.chainoptim.features.supply.service.SupplierOrderService;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceSeekerServiceImpl implements ResourceSeekerService {

    private final WarehouseService warehouseService;
    private final SupplierOrderService supplierOrderService;

    @Autowired
    public ResourceSeekerServiceImpl(WarehouseService warehouseService, SupplierOrderService supplierOrderService) {
        this.warehouseService = warehouseService;
        this.supplierOrderService = supplierOrderService;
    }

    public void seekResources(Integer organizationId, List<ResourceAllocation> allocationDeficits) {

    }

    private void seekWarehouseResources(Integer organizationId, List<ResourceAllocation> allocationDeficits) {
        List<Warehouse> warehouses = warehouseService.getWarehousesByOrganizationId(organizationId);


    }
}

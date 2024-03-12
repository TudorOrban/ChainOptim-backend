package org.chainoptim.features.evaluation.production.resourceallocation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.ResourceAllocation;
import org.chainoptim.features.supply.service.SupplierOrderService;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.features.warehouse.service.WarehouseInventoryService;
import org.chainoptim.features.warehouse.service.WarehouseService;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ResourceSeekerServiceImpl implements ResourceSeekerService {

    private final WarehouseService warehouseService;
    private final WarehouseInventoryService warehouseInventoryService;
    private final SupplierOrderService supplierOrderService;

    @Autowired
    public ResourceSeekerServiceImpl(
            WarehouseService warehouseService,
            WarehouseInventoryService warehouseInventoryService,
            SupplierOrderService supplierOrderService) {
        this.warehouseService = warehouseService;
        this.warehouseInventoryService = warehouseInventoryService;

        this.supplierOrderService = supplierOrderService;
    }

    public void seekResources(Integer organizationId, List<ResourceAllocation> allocationDeficits, Location factoryLocation) {

    }

    private void seekWarehouseResources(Integer organizationId, List<ResourceAllocation> allocationDeficits, Location factoryLocation) {
        List<Warehouse> warehouses = warehouseService.getWarehousesByOrganizationId(organizationId);

        for (Warehouse warehouse : warehouses) {
            // Skip if too far
            if (!areCloseEnough(warehouse.getLocation(), factoryLocation, 10.0f)) continue;

            // Get warehouse inventory
            List<WarehouseInventoryItem> inventory = warehouseInventoryService.getWarehouseInventoryItemsByWarehouseId(warehouse.getId());

            for (ResourceAllocation resourceAllocation : allocationDeficits) {
                WarehouseInventoryItem correspondingItem = inventory.stream()
                        .filter(ii -> Objects.equals(ii.getComponent().getId(), resourceAllocation.getComponentId())).findAny().orElse(null);


            }


        }


    }

    private boolean areCloseEnough(Location location1, Location location2, Float distance) {
        return location1.getLatitude() - location2.getLatitude() < distance &&
                location1.getLongitude() - location2.getLongitude() < distance;
    }
}

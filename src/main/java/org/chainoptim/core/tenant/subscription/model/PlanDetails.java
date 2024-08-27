package org.chainoptim.core.tenant.subscription.model;

import lombok.*;

@Data
@Builder
public class PlanDetails {

    private int pricePerMonthDollars;

    // Organization
    private int maxMembers;
    private int maxRoles;

    // Notifications
    private boolean realTimeNotificationsOn;
    private boolean emailNotificationsOn;
    private boolean customNotificationsOn;

    // Products
    private int maxProducts;
    private int maxComponents;
    private int maxProductStages;
    private int maxTransportRoutes;
    private int maxPricings;

    // Factories
    private int maxFactories;
    private int maxFactoryStages;
    private int maxFactoryInventoryItems;
    private boolean factoryPerformanceOn;

    // Warehouses
    private int maxWarehouses;
    private int maxWarehouseInventoryItems;
    private int maxCompartments;
    private int maxCrates;

    // Suppliers
    private int maxSuppliers;
    private int maxSupplierOrders;
    private int maxSupplierShipments;
    private boolean supplierPerformanceOn;

    // Clients
    private int maxClients;
    private int maxClientOrders;
    private int maxClientShipments;
    private boolean clientPerformanceOn;

    // Location
    private int maxLocations;
}

package org.chainoptim.core.subscription.model;

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

    // Factories
    private int maxFactories;
    private int maxFactoryStages;
    private int maxFactoryInventoryItems;
    private boolean factoryPerformanceOn;

    // Warehouses
    private int maxWarehouses;
    private int maxWarehouseInventoryItems;

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
}

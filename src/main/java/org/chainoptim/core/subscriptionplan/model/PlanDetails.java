package org.chainoptim.core.subscriptionplan.model;

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
    private int maxStagesPerProduct;

    // Factories
    private int maxFactories;
    private int maxStagesPerFactory;
    private int maxInventoryItemsPerFactory;
    private boolean factoryPerformanceOn;

    // Warehouses
    private int maxWarehouses;
    private int maxInventoryItemsPerWarehouse;

    // Suppliers
    private int maxSuppliers;
    private int maxOrdersPerSupplier;
    private int maxShipmentsPerSupplierOrder;
    private boolean supplierPerformanceOn;

    // Clients
    private int maxClients;
    private int maxOrdersPerClient;
    private int maxShipmentsPerClientOrder;
    private boolean clientPerformanceOn;
}

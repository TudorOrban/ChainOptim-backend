package org.chainoptim.shared.enums;

public enum Feature {

    // Organization
    MEMBER,
    CUSTOM_ROLE,

    // Products
    PRODUCT,
    PRODUCT_STAGE,
    COMPONENT,
    UNIT_OF_MEASUREMENT,

    // Factories
    FACTORY,
    FACTORY_STAGE,
    RESOURCE_ALLOCATION_PLAN,
    FACTORY_INVENTORY,
    FACTORY_PRODUCTION_HISTORY,
    FACTORY_PERFORMANCE,

    // Warehouses
    WAREHOUSE,
    WAREHOUSE_INVENTORY,


    // Suppliers
    SUPPLIER,
    SUPPLIER_ORDER,
    SUPPLIER_SHIPMENT,
    SUPPLIER_PERFORMANCE,

    // Clients
    CLIENT,
    CLIENT_ORDER,
    CLIENT_SHIPMENT,
    CLIENT_EVALUATION;

    @Override
    public String toString() {
        return switch (this) {
            case MEMBER -> "Member";
            case CUSTOM_ROLE -> "Custom Role";
            case PRODUCT -> "Product";
            case PRODUCT_STAGE -> "Product Stage";
            case COMPONENT -> "Component";
            case UNIT_OF_MEASUREMENT -> "Unit of Measurement";
            case FACTORY -> "Factory";
            case FACTORY_STAGE -> "Factory Stage";
            case RESOURCE_ALLOCATION_PLAN -> "Resource Allocation Plan";
            case FACTORY_INVENTORY -> "Factory Inventory";
            case FACTORY_PRODUCTION_HISTORY -> "Factory Production History";
            case FACTORY_PERFORMANCE -> "Factory Performance";
            case WAREHOUSE -> "Warehouse";
            case WAREHOUSE_INVENTORY -> "Warehouse Inventory";
            case SUPPLIER -> "Supplier";
            case SUPPLIER_ORDER -> "Supplier Order";
            case SUPPLIER_SHIPMENT -> "Supplier Shipment";
            case SUPPLIER_PERFORMANCE -> "Supplier Performance";
            case CLIENT -> "Client";
            case CLIENT_ORDER -> "Client Order";
            case CLIENT_SHIPMENT -> "Client Shipment";
            case CLIENT_EVALUATION -> "Client Evaluation";
        };
    }

    public Feature fromString(String feature) {
        return switch (feature) {
            case "Member" -> MEMBER;
            case "Custom Role" -> CUSTOM_ROLE;
            case "Product" -> PRODUCT;
            case "Product Stage" -> PRODUCT_STAGE;
            case "Component" -> COMPONENT;
            case "Unit of Measurement" -> UNIT_OF_MEASUREMENT;
            case "Factory" -> FACTORY;
            case "Factory Stage" -> FACTORY_STAGE;
            case "Resource Allocation Plan" -> RESOURCE_ALLOCATION_PLAN;
            case "Factory Inventory" -> FACTORY_INVENTORY;
            case "Factory Production History" -> FACTORY_PRODUCTION_HISTORY;
            case "Factory Performance" -> FACTORY_PERFORMANCE;
            case "Warehouse" -> WAREHOUSE;
            case "Warehouse Inventory" -> WAREHOUSE_INVENTORY;
            case "Supplier" -> SUPPLIER;
            case "Supplier Order" -> SUPPLIER_ORDER;
            case "Supplier Shipment" -> SUPPLIER_SHIPMENT;
            case "Supplier Performance" -> SUPPLIER_PERFORMANCE;
            case "Client" -> CLIENT;
            case "Client Order" -> CLIENT_ORDER;
            case "Client Shipment" -> CLIENT_SHIPMENT;
            case "Client Evaluation" -> CLIENT_EVALUATION;
            default -> throw new IllegalArgumentException("Unsupported feature: " + feature);
        };
    }
}

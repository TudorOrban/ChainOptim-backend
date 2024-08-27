package org.chainoptim.shared.enums;

public enum Feature {

    // Organization
    MEMBER,
    CUSTOM_ROLE,

    // Products
    PRODUCT,
    PRODUCT_STAGE,
    COMPONENT,
    TRANSPORT_ROUTE,
    PRICING,

    // Factories
    FACTORY,
    FACTORY_STAGE,
    FACTORY_INVENTORY,
    RESOURCE_ALLOCATION_PLAN,
    FACTORY_PRODUCTION_HISTORY,
    FACTORY_PERFORMANCE,

    // Warehouses
    WAREHOUSE,
    WAREHOUSE_INVENTORY,
    COMPARTMENT,
    CRATE,

    // Suppliers
    SUPPLIER,
    SUPPLIER_ORDER,
    SUPPLIER_SHIPMENT,
    SUPPLIER_PERFORMANCE,

    // Clients
    CLIENT,
    CLIENT_ORDER,
    CLIENT_SHIPMENT,
    CLIENT_EVALUATION,

    // Overview
    UPCOMING_EVENT,
    LOCATION,

    NONE;

    @Override
    public String toString() {
        return switch (this) {
            case MEMBER -> "Member";
            case CUSTOM_ROLE -> "Custom Role";
            case PRODUCT -> "Product";
            case PRODUCT_STAGE -> "Product Stage";
            case COMPONENT -> "Component";
            case TRANSPORT_ROUTE -> "Transport Route";
            case PRICING -> "Pricing";
            case FACTORY -> "Factory";
            case FACTORY_STAGE -> "Factory Stage";
            case FACTORY_INVENTORY -> "Factory Inventory";
            case RESOURCE_ALLOCATION_PLAN -> "Resource Allocation Plan";
            case FACTORY_PRODUCTION_HISTORY -> "Factory Production History";
            case FACTORY_PERFORMANCE -> "Factory Performance";
            case WAREHOUSE -> "Warehouse";
            case WAREHOUSE_INVENTORY -> "Warehouse Inventory";
            case COMPARTMENT -> "Compartment";
            case CRATE -> "Crate";
            case SUPPLIER -> "Supplier";
            case SUPPLIER_ORDER -> "Supplier Order";
            case SUPPLIER_SHIPMENT -> "Supplier Shipment";
            case SUPPLIER_PERFORMANCE -> "Supplier Performance";
            case CLIENT -> "Client";
            case CLIENT_ORDER -> "Client Order";
            case CLIENT_SHIPMENT -> "Client Shipment";
            case CLIENT_EVALUATION -> "Client Evaluation";
            case UPCOMING_EVENT -> "Upcoming Event";
            case LOCATION -> "Location";
            case NONE -> "None";
        };
    }

    public static Feature fromString(String feature) {
        return switch (feature) {
            case "Member" -> MEMBER;
            case "Custom Role" -> CUSTOM_ROLE;
            case "Product" -> PRODUCT;
            case "Product Stage" -> PRODUCT_STAGE;
            case "Component" -> COMPONENT;
            case "Transport Route" -> TRANSPORT_ROUTE;
            case "Pricing" -> PRICING;
            case "Factory" -> FACTORY;
            case "Factory Stage" -> FACTORY_STAGE;
            case "Factory Inventory" -> FACTORY_INVENTORY;
            case "Resource Allocation Plan" -> RESOURCE_ALLOCATION_PLAN;
            case "Factory Production History" -> FACTORY_PRODUCTION_HISTORY;
            case "Factory Performance" -> FACTORY_PERFORMANCE;
            case "Warehouse" -> WAREHOUSE;
            case "Warehouse Inventory" -> WAREHOUSE_INVENTORY;
            case "Compartment" -> COMPARTMENT;
            case "Crate" -> CRATE;
            case "Supplier" -> SUPPLIER;
            case "Supplier Order" -> SUPPLIER_ORDER;
            case "Supplier Shipment" -> SUPPLIER_SHIPMENT;
            case "Supplier Performance" -> SUPPLIER_PERFORMANCE;
            case "Client" -> CLIENT;
            case "Client Order" -> CLIENT_ORDER;
            case "Client Shipment" -> CLIENT_SHIPMENT;
            case "Client Evaluation" -> CLIENT_EVALUATION;
            case "Upcoming Event" -> UPCOMING_EVENT;
            case "Location" -> LOCATION;
            case "None" -> NONE;
            default -> throw new IllegalArgumentException("Unsupported feature: " + feature);
        };
    }
}

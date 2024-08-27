package org.chainoptim.core.subscription.model;

import org.chainoptim.core.organization.model.SubscriptionPlanTier;

import lombok.Getter;

import java.util.Map;

@Getter
public class BaseSubscriptionPlans {

    private BaseSubscriptionPlans() {}

    private static final PlanDetails NONE_PLAN = PlanDetails.builder()
            .pricePerMonthDollars(0)
            .maxMembers(2)
            .maxRoles(2)
            .realTimeNotificationsOn(false)
            .emailNotificationsOn(false)
            .customNotificationsOn(false)
            .maxProducts(1)
            .maxComponents(1)
            .maxProductStages(3)
            .maxTransportRoutes(3)
            .maxPricings(1)
            .maxFactories(1)
            .maxFactoryStages(3)
            .maxFactoryInventoryItems(5)
            .factoryPerformanceOn(false)
            .maxWarehouses(1)
            .maxWarehouseInventoryItems(5)
            .maxCompartments(2)
            .maxCrates(3)
            .maxSuppliers(1)
            .maxSupplierOrders(5)
            .maxSupplierShipments(3)
            .supplierPerformanceOn(false)
            .maxClients(1)
            .maxClientOrders(5)
            .maxClientShipments(3)
            .clientPerformanceOn(false)
            .maxLocations(2)
            .build();

    private static final PlanDetails BASIC_PLAN = PlanDetails.builder()
            .pricePerMonthDollars(30)
            .maxMembers(10)
            .maxRoles(5)
            .realTimeNotificationsOn(false)
            .emailNotificationsOn(false)
            .customNotificationsOn(false)
            .maxProducts(10)
            .maxComponents(10)
            .maxProductStages(25)
            .maxTransportRoutes(50)
            .maxPricings(10)
            .maxFactories(10)
            .maxFactoryStages(25)
            .maxFactoryInventoryItems(50)
            .factoryPerformanceOn(false)
            .maxWarehouses(10)
            .maxWarehouseInventoryItems(50)
            .maxCompartments(2)
            .maxCrates(3)
            .maxSuppliers(10)
            .maxSupplierOrders(50)
            .maxSupplierShipments(50)
            .supplierPerformanceOn(false)
            .maxClients(10)
            .maxClientOrders(50)
            .maxClientShipments(50)
            .clientPerformanceOn(false)
            .maxLocations(20)
            .build();

    private static final PlanDetails PROFESSIONAL_PLAN = PlanDetails.builder()
            .pricePerMonthDollars(300)
            .maxMembers(25)
            .maxRoles(10)
            .realTimeNotificationsOn(true)
            .emailNotificationsOn(false)
            .customNotificationsOn(false)
            .maxProducts(25)
            .maxComponents(25)
            .maxProductStages(50)
            .maxTransportRoutes(100)
            .maxPricings(10)
            .maxFactories(25)
            .maxFactoryStages(50)
            .maxFactoryInventoryItems(250)
            .factoryPerformanceOn(false)
            .maxWarehouses(25)
            .maxWarehouseInventoryItems(250)
            .maxCompartments(50)
            .maxCrates(50)
            .maxSuppliers(25)
            .maxSupplierOrders(250)
            .maxSupplierShipments(500)
            .supplierPerformanceOn(false)
            .maxClients(25)
            .maxClientOrders(250)
            .maxClientShipments(500)
            .clientPerformanceOn(false)
            .maxLocations(50)
            .build();

    private static final PlanDetails ENTERPRISE_PLAN = PlanDetails.builder()
            .pricePerMonthDollars(1200)
            .maxMembers(-1) // Unlimited marker
            .maxRoles(-1)
            .realTimeNotificationsOn(true)
            .emailNotificationsOn(true)
            .customNotificationsOn(true)
            .maxProducts(-1)
            .maxComponents(-1)
            .maxProductStages(-1)
            .maxTransportRoutes(-1)
            .maxPricings(-1)
            .maxFactories(-1)
            .maxFactoryStages(-1)
            .maxFactoryInventoryItems(-1)
            .factoryPerformanceOn(true)
            .maxWarehouses(-1)
            .maxWarehouseInventoryItems(-1)
            .maxCompartments(-1)
            .maxCrates(-1)
            .maxSuppliers(-1)
            .maxSupplierOrders(-1)
            .maxSupplierShipments(-1)
            .supplierPerformanceOn(true)
            .maxClients(-1)
            .maxClientOrders(-1)
            .maxClientShipments(-1)
            .clientPerformanceOn(true)
            .maxLocations(-1)
            .build();

    private static final Map<SubscriptionPlanTier, PlanDetails> PLANS = Map.of(
            SubscriptionPlanTier.NONE, NONE_PLAN,
            SubscriptionPlanTier.BASIC, BASIC_PLAN,
            SubscriptionPlanTier.PROFESSIONAL, PROFESSIONAL_PLAN,
            SubscriptionPlanTier.ENTERPRISE, ENTERPRISE_PLAN
    );

    public static Map<SubscriptionPlanTier, PlanDetails> getPlans() {
        return PLANS;
    }

    public static PlanDetails getPlan(SubscriptionPlanTier tier) {
        return PLANS.get(tier);
    }
}

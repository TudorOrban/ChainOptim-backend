package org.chainoptim.core.subscriptionplan.model;

import org.chainoptim.core.organization.model.Organization;

import lombok.Getter;

import java.util.Map;

@Getter
public class SubscriptionPlans {

    private SubscriptionPlans() {}

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
            .maxFactories(1)
            .maxFactoryStages(3)
            .maxFactoryInventoryItems(5)
            .factoryPerformanceOn(false)
            .maxWarehouses(1)
            .maxWarehouseInventoryItems(5)
            .maxSuppliers(1)
            .maxSupplierOrders(5)
            .maxSupplierShipments(3)
            .supplierPerformanceOn(false)
            .maxClients(1)
            .maxClientOrders(5)
            .maxClientShipments(3)
            .clientPerformanceOn(false)
            .build();

    private static final PlanDetails BASIC_PLAN = PlanDetails.builder()
            .pricePerMonthDollars(300)
            .maxMembers(10)
            .maxRoles(10)
            .realTimeNotificationsOn(true)
            .emailNotificationsOn(false)
            .customNotificationsOn(false)
            .maxProducts(10)
            .maxComponents(20)
            .maxProductStages(50)
            .maxFactories(10)
            .maxFactoryStages(50)
            .maxFactoryInventoryItems(250)
            .factoryPerformanceOn(false)
            .maxWarehouses(10)
            .maxWarehouseInventoryItems(250)
            .maxSuppliers(10)
            .maxSupplierOrders(250)
            .maxSupplierShipments(500)
            .supplierPerformanceOn(false)
            .maxClients(10)
            .maxClientOrders(250)
            .maxClientShipments(500)
            .clientPerformanceOn(false)
            .build();

    private static final PlanDetails PRO_PLAN = PlanDetails.builder()
            .pricePerMonthDollars(1200)
            .maxMembers(-1) // Unlimited marker
            .maxRoles(-1)
            .realTimeNotificationsOn(true)
            .emailNotificationsOn(true)
            .customNotificationsOn(true)
            .maxProducts(-1)
            .maxComponents(-1)
            .maxProductStages(-1)
            .maxFactories(-1)
            .maxFactoryStages(-1)
            .maxFactoryInventoryItems(-1)
            .factoryPerformanceOn(true)
            .maxWarehouses(-1)
            .maxWarehouseInventoryItems(-1)
            .maxSuppliers(-1)
            .maxSupplierOrders(-1)
            .maxSupplierShipments(-1)
            .supplierPerformanceOn(true)
            .maxClients(-1)
            .maxClientOrders(-1)
            .maxClientShipments(-1)
            .clientPerformanceOn(true)
            .build();

    private static final Map<Organization.SubscriptionPlanTier, PlanDetails> PLANS = Map.of(
            Organization.SubscriptionPlanTier.NONE, NONE_PLAN,
            Organization.SubscriptionPlanTier.BASIC, BASIC_PLAN,
            Organization.SubscriptionPlanTier.PRO, PRO_PLAN
    );

    public static Map<Organization.SubscriptionPlanTier, PlanDetails> getPlans() {
        return PLANS;
    }
}

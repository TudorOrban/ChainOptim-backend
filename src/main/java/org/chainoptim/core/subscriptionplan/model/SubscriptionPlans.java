package org.chainoptim.core.subscriptionplan.model;

import org.chainoptim.core.organization.model.Organization;

import lombok.Getter;

import java.util.Map;

@Getter
public class SubscriptionPlans {

    private SubscriptionPlans() {
    }
    private static final PlanDetails NONE_PLAN = PlanDetails.builder()
            .maxMembers(2)
            .maxRoles(2)
            .realTimeNotificationsOn(false)
            .emailNotificationsOn(false)
            .customNotificationsOn(false)
            .maxProducts(1)
            .maxComponents(1)
            .maxStagesPerProduct(3)
            .maxFactories(1)
            .maxStagesPerFactory(3)
            .maxInventoryItemsPerFactory(5)
            .factoryPerformanceOn(false)
            .maxWarehouses(1)
            .maxInventoryItemsPerWarehouse(5)
            .maxSuppliers(1)
            .maxOrdersPerSupplier(5)
            .maxShipmentsPerSupplierOrder(3)
            .supplierPerformanceOn(false)
            .maxClients(1)
            .maxOrdersPerClient(5)
            .maxShipmentsPerClientOrder(3)
            .clientPerformanceOn(false)
            .build();

    private static final PlanDetails BASIC_PLAN = PlanDetails.builder()
            .maxMembers(10)
            .maxRoles(10)
            .realTimeNotificationsOn(true)
            .emailNotificationsOn(false)
            .customNotificationsOn(false)
            .maxProducts(10)
            .maxComponents(20)
            .maxStagesPerProduct(10)
            .maxFactories(10)
            .maxStagesPerFactory(10)
            .maxInventoryItemsPerFactory(20)
            .factoryPerformanceOn(false)
            .maxWarehouses(10)
            .maxInventoryItemsPerWarehouse(20)
            .maxSuppliers(10)
            .maxOrdersPerSupplier(20)
            .maxShipmentsPerSupplierOrder(10)
            .supplierPerformanceOn(false)
            .maxClients(10)
            .maxOrdersPerClient(20)
            .maxShipmentsPerClientOrder(10)
            .clientPerformanceOn(false)
            .build();

    private static final PlanDetails PRO_PLAN = PlanDetails.builder()
            .maxMembers(-1) // Unlimited marker
            .maxRoles(-1)
            .realTimeNotificationsOn(true)
            .emailNotificationsOn(true)
            .customNotificationsOn(true)
            .maxProducts(-1)
            .maxComponents(-1)
            .maxStagesPerProduct(-1)
            .maxFactories(-1)
            .maxStagesPerFactory(-1)
            .maxInventoryItemsPerFactory(-1)
            .factoryPerformanceOn(true)
            .maxWarehouses(-1)
            .maxInventoryItemsPerWarehouse(-1)
            .maxSuppliers(-1)
            .maxOrdersPerSupplier(-1)
            .maxShipmentsPerSupplierOrder(-1)
            .supplierPerformanceOn(true)
            .maxClients(-1)
            .maxOrdersPerClient(-1)
            .maxShipmentsPerClientOrder(-1)
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

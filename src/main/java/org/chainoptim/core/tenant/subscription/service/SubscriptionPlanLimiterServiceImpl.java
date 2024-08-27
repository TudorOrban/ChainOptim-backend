package org.chainoptim.core.tenant.subscription.service;

import org.chainoptim.core.tenant.organization.model.SubscriptionPlanTier;
import org.chainoptim.core.tenant.subscription.model.PlanDetails;
import org.chainoptim.core.tenant.subscription.model.BaseSubscriptionPlans;
import org.chainoptim.core.tenant.subscription.model.SubscriptionPlan;
import org.chainoptim.core.tenant.subscription.repository.SubscriptionPlanRepository;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.enums.Feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionPlanLimiterServiceImpl implements SubscriptionPlanLimiterService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final FeatureCounterService featureCounterService;

    @Autowired
    public SubscriptionPlanLimiterServiceImpl(SubscriptionPlanRepository subscriptionPlanRepository,
                                              FeatureCounterService featureCounterService) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.featureCounterService = featureCounterService;
    }

    public boolean isLimitReached(Integer organizationId, Feature feature, Integer quantity) {
        // Get the current subscription plan
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findByOrganizationId(organizationId);
        if (plans.isEmpty()) {
            throw new ResourceNotFoundException("Subscription plan not found for organization with ID: " + organizationId);
        }
        SubscriptionPlan currentPlan = plans.getFirst();

        if (Boolean.FALSE.equals(currentPlan.getIsActive()) || Boolean.FALSE.equals(currentPlan.getIsPaid())) {
            return true;
        }

        // Determine limit for the feature
        long maxCount = getMaxCount(currentPlan, feature);
        if (maxCount == -1) return false; // No limits for ENTERPRISE plan

        long currentCount = featureCounterService.getCountByFeature(organizationId, feature);

        return isAboveLimit(currentCount, maxCount, quantity);
    }

    private long getMaxCount(SubscriptionPlan currentPlan, Feature feature) {
        if (currentPlan.getCustomPlan().getPlanTier().equals(SubscriptionPlanTier.ENTERPRISE)) return -1;

        PlanDetails planDetails = BaseSubscriptionPlans.getPlans().get(currentPlan.getCustomPlan().getPlanTier());

        long baseFeatureCount = getBaseMaxCountByFeature(feature, planDetails);

        if (Boolean.TRUE.equals(!currentPlan.getIsBasic()) && currentPlan.getCustomPlan().getAdditionalFeatures() != null) {
            baseFeatureCount += currentPlan.getCustomPlan().getAdditionalFeatures().getOrDefault(feature, 0L);
        }

        return baseFeatureCount;
    }

    private long getBaseMaxCountByFeature(Feature feature, PlanDetails planDetails) {
        return switch (feature) {
            case MEMBER -> planDetails.getMaxMembers();
            case PRODUCT -> planDetails.getMaxProducts();
            case PRODUCT_STAGE -> planDetails.getMaxProductStages();
            case COMPONENT -> planDetails.getMaxComponents();
            case TRANSPORT_ROUTE -> planDetails.getMaxTransportRoutes();
            case PRICING -> planDetails.getMaxPricings();
            case FACTORY -> planDetails.getMaxFactories();
            case FACTORY_STAGE -> planDetails.getMaxFactoryStages();
            case FACTORY_INVENTORY -> planDetails.getMaxFactoryInventoryItems();
            case WAREHOUSE -> planDetails.getMaxWarehouses();
            case WAREHOUSE_INVENTORY -> planDetails.getMaxWarehouseInventoryItems();
            case COMPARTMENT -> planDetails.getMaxCompartments();
            case CRATE -> planDetails.getMaxCrates();
            case SUPPLIER -> planDetails.getMaxSuppliers();
            case SUPPLIER_ORDER -> planDetails.getMaxSupplierOrders();
            case SUPPLIER_SHIPMENT -> planDetails.getMaxSupplierShipments();
            case CLIENT -> planDetails.getMaxClients();
            case CLIENT_ORDER -> planDetails.getMaxClientOrders();
            case CLIENT_SHIPMENT -> planDetails.getMaxClientShipments();
            case LOCATION -> planDetails.getMaxLocations();
            default -> 0L;
        };
    }

    private boolean isAboveLimit(Long currentCount, Long maxCount, Integer quantity) {
        return currentCount + quantity >= maxCount;
    }
}

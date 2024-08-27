package org.chainoptim.core.subscription.service;

import org.chainoptim.core.organization.model.SubscriptionPlanTier;
import org.chainoptim.core.subscription.model.PlanDetails;
import org.chainoptim.core.subscription.model.BaseSubscriptionPlans;
import org.chainoptim.core.subscription.model.SubscriptionPlan;
import org.chainoptim.core.subscription.repository.SubscriptionPlanRepository;
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

        if (Boolean.FALSE.equals(currentPlan.getIsBasic())) {
            baseFeatureCount += currentPlan.getCustomPlan().getAdditionalFeatures().getOrDefault(feature, 0L);
        }

        return baseFeatureCount;
    }

    private long getBaseMaxCountByFeature(Feature feature, PlanDetails planDetails) {
        return switch (feature) {
            case Feature.MEMBER -> planDetails.getMaxMembers();
            case Feature.PRODUCT -> planDetails.getMaxProducts();
            case Feature.PRODUCT_STAGE -> planDetails.getMaxProductStages();
            case Feature.COMPONENT -> planDetails.getMaxComponents();
            case Feature.FACTORY -> planDetails.getMaxFactories();
            case Feature.FACTORY_STAGE -> planDetails.getMaxFactoryStages();
            case Feature.FACTORY_INVENTORY -> planDetails.getMaxFactoryInventoryItems();
            case Feature.WAREHOUSE -> planDetails.getMaxWarehouses();
            case Feature.WAREHOUSE_INVENTORY -> planDetails.getMaxWarehouseInventoryItems();
            case Feature.SUPPLIER -> planDetails.getMaxSuppliers();
            case Feature.SUPPLIER_ORDER -> planDetails.getMaxSupplierOrders();
            case Feature.SUPPLIER_SHIPMENT -> planDetails.getMaxSupplierShipments();
            case Feature.CLIENT -> planDetails.getMaxClients();
            case Feature.CLIENT_ORDER -> planDetails.getMaxClientOrders();
            case Feature.CLIENT_SHIPMENT -> planDetails.getMaxClientShipments();
            default -> 0L;
        };
    }

    private boolean isAboveLimit(Long currentCount, Long maxCount, Integer quantity) {
        return currentCount + quantity >= maxCount;
    }
}

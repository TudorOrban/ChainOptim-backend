package org.chainoptim.core.subscriptionplan.service;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.overview.model.SupplyChainSnapshot;
import org.chainoptim.core.overview.service.SupplyChainSnapshotService;
import org.chainoptim.core.subscriptionplan.model.PlanDetails;
import org.chainoptim.core.subscriptionplan.model.SubscriptionPlans;
import org.chainoptim.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPlanLimiterServiceImpl implements SubscriptionPlanLimiterService {

    private final SupplyChainSnapshotService supplyChainSnapshotService;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public SubscriptionPlanLimiterServiceImpl(SupplyChainSnapshotService supplyChainSnapshotService,
                                              OrganizationRepository organizationRepository) {
        this.supplyChainSnapshotService = supplyChainSnapshotService;
        this.organizationRepository = organizationRepository;
    }

    public boolean isLimitReached(Integer organizationId, String featureName) {
        SupplyChainSnapshot snapshot = supplyChainSnapshotService.getSupplyChainSnapshot(organizationId);
        Organization.SubscriptionPlanTier planTier = organizationRepository.getSubscriptionPlanTierById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID: " + organizationId + " not found"));
        PlanDetails planDetails = SubscriptionPlans.getPlans().get(planTier);

        return switch (featureName) {
            case "Products" -> snapshot.getProductsCount() >= planDetails.getMaxProducts();
            case "Factories" -> snapshot.getFactoriesCount() >= planDetails.getMaxFactories();
            case "Warehouses" -> snapshot.getWarehousesCount() >= planDetails.getMaxWarehouses();
            case "Suppliers" -> snapshot.getSuppliersCount() >= planDetails.getMaxSuppliers();
            case "Clients" -> snapshot.getClientsCount() >= planDetails.getMaxClients();
            default -> true; // Don't restrict here for now
        };
    }
}

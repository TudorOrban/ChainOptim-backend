package org.chainoptim.core.subscriptionplan.service;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.service.SnapshotPersistenceService;
import org.chainoptim.core.subscriptionplan.model.PlanDetails;
import org.chainoptim.core.subscriptionplan.model.SubscriptionPlans;
import org.chainoptim.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPlanLimiterServiceImpl implements SubscriptionPlanLimiterService {

    private final SnapshotPersistenceService snapshotPersistenceService;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public SubscriptionPlanLimiterServiceImpl(SnapshotPersistenceService snapshotPersistenceService,
                                              OrganizationRepository organizationRepository) {
        this.snapshotPersistenceService = snapshotPersistenceService;
        this.organizationRepository = organizationRepository;
    }

    public boolean isLimitReached(Integer organizationId, String featureName, Integer quantity) {
        Snapshot snapshot = snapshotPersistenceService.getSupplyChainSnapshotByOrganizationId(organizationId).getSnapshot();
        Organization.SubscriptionPlanTier planTier = organizationRepository.getSubscriptionPlanTierById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID: " + organizationId + " not found"));
        PlanDetails planDetails = SubscriptionPlans.getPlans().get(planTier);

        if (planTier.equals(Organization.SubscriptionPlanTier.PRO)) return false; // No limits for PRO plan

        return switch (featureName) {
            case "Products" -> snapshot.getProductsCount() >= planDetails.getMaxProducts() + quantity;
            case "Factories" -> snapshot.getFactoriesCount() >= planDetails.getMaxFactories() + quantity;
            case "Factory Inventory Items" -> snapshot.getFactoryInventoryItemsCount() >= planDetails.getMaxFactoryInventoryItems() + quantity;
            case "Warehouses" -> snapshot.getWarehousesCount() >= planDetails.getMaxWarehouses() + quantity;
            case "Warehouse Inventory Items" -> snapshot.getWarehouseInventoryItemsCount() >= planDetails.getMaxWarehouseInventoryItems() + quantity;
            case "Suppliers" -> snapshot.getSuppliersCount() >= planDetails.getMaxSuppliers() + quantity;
            case "Supplier Orders" -> snapshot.getSupplierOrdersCount() >= planDetails.getMaxSupplierOrders() + quantity;
            case "Clients" -> snapshot.getClientsCount() >= planDetails.getMaxClients() + quantity;
            case "Client Orders" -> snapshot.getClientOrdersCount() >= planDetails.getMaxClientOrders() + quantity;
            default -> true; // Don't restrict here for now
        };
    }
}

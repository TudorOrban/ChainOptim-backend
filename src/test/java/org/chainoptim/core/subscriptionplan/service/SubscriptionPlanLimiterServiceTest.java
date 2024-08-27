package org.chainoptim.core.subscriptionplan.service;

import org.chainoptim.core.tenant.organization.model.SubscriptionPlanTier;
import org.chainoptim.core.tenant.organization.repository.OrganizationRepository;
import org.chainoptim.core.overview.scsnapshot.model.Snapshot;
import org.chainoptim.core.overview.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.overview.scsnapshot.service.SnapshotPersistenceService;
import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterServiceImpl;
import org.chainoptim.shared.enums.Feature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionPlanLimiterServiceTest {

    @Mock
    private SnapshotPersistenceService snapshotPersistenceService;

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private SubscriptionPlanLimiterServiceImpl subscriptionPlanLimiterService;

    private final Integer organizationId = 1;

    @Test
    void testIsLimitReached_ProPlan() {
        // Arrange
        createTestSnapshot(0);
        when(organizationRepository.getSubscriptionPlanTierById(anyInt())).thenReturn(Optional.of(SubscriptionPlanTier.PROFESSIONAL));

        // Act
        boolean isLimitReached = subscriptionPlanLimiterService.isLimitReached(organizationId, Feature.PRODUCT, 0);

        // Assert
        assertFalse(isLimitReached);
    }

    @Test
    void testIsLimitReached_ProductLimitReached() {
        // Arrange
        createTestSnapshot(11);
        when(organizationRepository.getSubscriptionPlanTierById(anyInt())).thenReturn(Optional.of(SubscriptionPlanTier.BASIC));

        // Act
        boolean isLimitReached = subscriptionPlanLimiterService.isLimitReached(organizationId, Feature.PRODUCT, 1);

        // Assert
        assertTrue(isLimitReached);
    }

    @Test
    void testIsLimitReached_ProductLimitNotReached() {
        // Arrange
        createTestSnapshot(8);
        when(organizationRepository.getSubscriptionPlanTierById(anyInt())).thenReturn(Optional.of(SubscriptionPlanTier.BASIC));

        // Act
        boolean isLimitReached = subscriptionPlanLimiterService.isLimitReached(organizationId, Feature.PRODUCT, 1);

        // Assert
        assertFalse(isLimitReached);
    }

    @Test
    void testIsLimitReached_NonePlan() {
        // Arrange
        createTestSnapshot(2);
        when(organizationRepository.getSubscriptionPlanTierById(anyInt())).thenReturn(Optional.of(SubscriptionPlanTier.NONE));

        // Act
        boolean isLimitReached = subscriptionPlanLimiterService.isLimitReached(organizationId, Feature.PRODUCT, 0);

        // Assert
        assertTrue(isLimitReached);
    }

    void createTestSnapshot(Integer productsCount) {
        SupplyChainSnapshot supplyChainSnapshot = new SupplyChainSnapshot();
        Snapshot snapshot = new Snapshot();
        snapshot.setProductsCount(productsCount);
        supplyChainSnapshot.setSnapshot(snapshot);
        supplyChainSnapshot.setOrganizationId(organizationId);

        when(snapshotPersistenceService.getSupplyChainSnapshotByOrganizationId(anyInt())).thenReturn(supplyChainSnapshot);
    }
}

package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.core.notifications.model.NotificationUserDistribution;
import org.chainoptim.core.organization.model.FeaturePermissions;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.model.Permissions;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.settings.model.NotificationSettings;
import org.chainoptim.core.settings.model.UserSettings;
import org.chainoptim.core.settings.repository.UserSettingsRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.shared.enums.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class NotificationDistributionServiceImpl implements NotificationDistributionService {

    private final SupplierRepository supplierRepository;
    private final ClientRepository clientRepository;
    private final OrganizationRepository organizationRepository;
    private final UserSettingsRepository userSettingsRepository;

    @Autowired
    public NotificationDistributionServiceImpl(SupplierRepository supplierRepository,
                                               ClientRepository clientRepository,
                                               OrganizationRepository organizationRepository,
                                               UserSettingsRepository userSettingsRepository) {
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
        this.organizationRepository = organizationRepository;
        this.userSettingsRepository = userSettingsRepository;
    }

    public NotificationUserDistribution distributeEventToUsers(SupplierOrderEvent event) {
        System.out.println("Distributing event: " + event);
        Integer organizationId = determineOrderOrganization(event);

        return distributeEventToUsers(organizationId, event.getEventType(), event.getEntityType());
    }

    public NotificationUserDistribution distributeEventToUsers(ClientOrderEvent event) {
        System.out.println("Distributing event: " + event);
        Integer organizationId = determineOrderOrganization(event);

        return distributeEventToUsers(organizationId, event.getEventType(), event.getEntityType());
    }

    private NotificationUserDistribution distributeEventToUsers(Integer organizationId, KafkaEvent.EventType eventType, Feature entityType) {
        // TODO: Cache this with Redis
        Organization organization = organizationRepository.findByIdWithUsersAndCustomRoles(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID: " + organizationId + " not found"));

        List<String> candidateUserIds = new ArrayList<>();
        List<User> emailCandidateUsers = new ArrayList<>();

        // Skip if subscription plan doesn't support notifications
        if (!organization.getSubscriptionPlan().isRealTimeNotificationsOn()) {
            return new NotificationUserDistribution(candidateUserIds, candidateUserIds);
        }

        determineMembersWithPermissions(organization.getUsers(), eventType, entityType, candidateUserIds, emailCandidateUsers);

        // Determine which candidate users should receive this event based on their settings
        List<UserSettings> userSettings = userSettingsRepository.findByUserIdIn(candidateUserIds);

        List<String> usersToReceiveNotification = candidateUserIds.stream().filter(userId -> {
            UserSettings settings = userSettings.stream().filter(userSetting -> userSetting.getUserId().equals(userId)).findFirst().orElse(null);
            return settings != null && shouldReceiveNotification(settings.getNotificationSettings(), entityType);
        }).toList();

        List<String> usersToReceiveEmail = emailCandidateUsers.stream().filter(user -> {
            UserSettings settings = userSettings.stream().filter(userSetting -> userSetting.getUserId().equals(user.getId())).findFirst().orElse(null);
            return settings != null && shouldReceiveEmailNotification(settings.getNotificationSettings(), entityType);
        }).map(User::getEmail).toList();

        return new NotificationUserDistribution(usersToReceiveNotification, usersToReceiveEmail);
    }

    private void determineMembersWithPermissions(Set<User> organizationMembers,
                                                 KafkaEvent.EventType eventType, Feature entityType,
                                                 List<String> candidateUserIds, List<User> emailCandidateUsers) {
        for (User user : organizationMembers) {
            if (user.getCustomRole() == null) {
                if (user.getRole().equals(User.Role.ADMIN)) {
                    candidateUserIds.add(user.getId());
                    emailCandidateUsers.add(user);
                }
                continue;
            }

            FeaturePermissions featurePermissions = getFeaturePermissions(user.getCustomRole().getPermissions(), entityType);
            if (featurePermissions == null) continue;
            if (hasPermissions(featurePermissions, eventType)) {
                candidateUserIds.add(user.getId());
                emailCandidateUsers.add(user);
            }
        }
    }

    private Integer determineOrderOrganization(SupplierOrderEvent event) {
        Long supplierId = Long.valueOf(event.getNewEntity() != null ? event.getNewEntity().getSupplierId() : event.getOldEntity().getSupplierId());
        return supplierRepository.findOrganizationIdById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID: " + event.getNewEntity().getSupplierId() + " not found"));
    }

    private Integer determineOrderOrganization(ClientOrderEvent event) {
        Long clientId = Long.valueOf(event.getNewEntity() != null ? event.getNewEntity().getClientId() : event.getOldEntity().getClientId());
        return clientRepository.findOrganizationIdById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + event.getNewEntity().getClientId() + " not found"));
    }

    private FeaturePermissions getFeaturePermissions(Permissions permissions, Feature entityType) {
        if (permissions == null) return null;
        return switch (entityType) {
            case SUPPLIER_ORDER -> permissions.getSuppliers();
            case CLIENT_ORDER -> permissions.getClients();
            case FACTORY_INVENTORY -> permissions.getFactories();
            case WAREHOUSE_INVENTORY -> permissions.getWarehouses();
            default -> null;
        };
    }

    private boolean hasPermissions(FeaturePermissions featurePermissions, KafkaEvent.EventType eventType) {
        return switch (eventType) {
            case CREATE -> Boolean.TRUE.equals(featurePermissions.getCanCreate());
            case UPDATE -> Boolean.TRUE.equals(featurePermissions.getCanUpdate());
            case DELETE -> Boolean.TRUE.equals(featurePermissions.getCanDelete());
        };
    }

    private boolean shouldReceiveNotification(NotificationSettings notificationSettings, Feature entityType) {
        return switch (entityType) {
            case SUPPLIER_ORDER -> notificationSettings.isSupplierOrdersOn();
            case CLIENT_ORDER -> notificationSettings.isClientOrdersOn();
            case FACTORY_INVENTORY -> notificationSettings.isFactoryInventoryOn();
            case WAREHOUSE_INVENTORY -> notificationSettings.isWarehouseInventoryOn();
            default -> false;
        };
    }

    private boolean shouldReceiveEmailNotification(NotificationSettings notificationSettings, Feature entityType) {
        return switch (entityType) {
            case SUPPLIER_ORDER -> notificationSettings.isEmailSupplierOrdersOn();
            case CLIENT_ORDER -> notificationSettings.isEmailClientOrdersOn();
            case FACTORY_INVENTORY -> notificationSettings.isEmailFactoryInventoryOn();
            case WAREHOUSE_INVENTORY -> notificationSettings.isEmailWarehouseInventoryOn();
            default -> false;
        };
    }
}

package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> distributeEventToUsers(SupplierOrderEvent event) {
        System.out.println("Distributing event: " + event);
        Integer organizationId = determineOrderOrganization(event);

        return distributeEventToUsers(organizationId, event.getEventType(), event.getEntityType());
    }

    public List<String> distributeEventToUsers(ClientOrderEvent event) {
        System.out.println("Distributing event: " + event);
        Integer organizationId = determineOrderOrganization(event);

        return distributeEventToUsers(organizationId, event.getEventType(), event.getEntityType());
    }

    private List<String> distributeEventToUsers(Integer organizationId, KafkaEvent.EventType eventType, String entityType) {
        // TODO: Cache this with Redis
        Organization organization = organizationRepository.findByIdWithUsersAndCustomRoles(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID: " + organizationId + " not found"));

        List<String> candidateUserIds = new ArrayList<>();

        // Determine which users have permissions to view this event
        for (User user : organization.getUsers()) {
            if (user.getCustomRole() != null) {
                FeaturePermissions featurePermissions = getFeaturePermissions(user.getCustomRole().getPermissions(), entityType);
                if (featurePermissions == null) continue;
                if (hasPermissions(featurePermissions, eventType)) {
                    candidateUserIds.add(user.getId());
                }
            } else {
                if (user.getRole().equals(User.Role.ADMIN)) {
                    candidateUserIds.add(user.getId());
                }
            }
        }

        // Determine which candidate users should receive this event based on their settings
        List<UserSettings> userSettings = userSettingsRepository.findByUserIdIn(candidateUserIds);
        return candidateUserIds.stream().filter(userId -> {
            UserSettings settings = userSettings.stream().filter(userSetting -> userSetting.getUserId().equals(userId)).findFirst().orElse(null);
            return settings != null && shouldReceiveNotification(settings.getNotificationSettings(), entityType);
        }).toList();
    }

    private Integer determineOrderOrganization(SupplierOrderEvent event) {
        return supplierRepository.findOrganizationIdById(Long.valueOf(event.getNewEntity().getSupplierId()))
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID: " + event.getNewEntity().getSupplierId() + " not found"));
    }

    private Integer determineOrderOrganization(ClientOrderEvent event) {
        return clientRepository.findOrganizationIdById(Long.valueOf(event.getNewEntity().getClientId()))
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + event.getNewEntity().getClientId() + " not found"));
    }

    private FeaturePermissions getFeaturePermissions(Permissions permissions, String entityType) {
        if (permissions == null) return null;
        return switch (entityType) {
            case "Supplier Order" -> permissions.getSuppliers();
            case "Client Order" -> permissions.getClients();
            case "Factory Inventory" -> permissions.getFactories();
            case "Warehouse Inventory" -> permissions.getWarehouses();
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

    private boolean shouldReceiveNotification(NotificationSettings notificationSettings, String entityType) {
        return switch (entityType) {
            case "Supplier Order" -> notificationSettings.isSupplierOrdersOn();
            case "Client Order" -> notificationSettings.isClientOrdersOn();
            case "Factory Inventory" -> notificationSettings.isFactoryInventoryOn();
            case "Warehouse Inventory" -> notificationSettings.isWarehouseInventoryOn();
            default -> false;
        };
    }
}

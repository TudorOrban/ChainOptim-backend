package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.core.organization.model.FeaturePermissions;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
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

    @Autowired
    public NotificationDistributionServiceImpl(SupplierRepository supplierRepository,
                                               ClientRepository clientRepository,
                                               OrganizationRepository organizationRepository) {
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
        this.organizationRepository = organizationRepository;
    }

    public List<String> distributeEventToUsers(SupplierOrderEvent event) {
        System.out.println("Distributing event: " + event);
        Integer organizationId = determineOrderOrganization(event);
        Organization organization = organizationRepository.findByIdWithUsers(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID: " + organizationId + " not found"));

        List<String> candidateUserIds = new ArrayList<>();

        for (User user : organization.getUsers()) {
            if (user.getCustomRole() != null) {
                FeaturePermissions supplierPermissions = user.getCustomRole().getPermissions().getSuppliers();
                if (hasPermissions(supplierPermissions, event.getEventType())) {
                    candidateUserIds.add(user.getId());
                }
            } else {
                if (user.getRole().equals(User.Role.ADMIN)) {
                    candidateUserIds.add(user.getId());
                }
            }
        }

        // TODO: Filter further by user settings

        return candidateUserIds;
    }

    public List<String> distributeEventToUsers(ClientOrderEvent event) {
        System.out.println("Distributing event: " + event);
        Integer organizationId = determineOrderOrganization(event);

        return List.of("086e9e96-a8ef-11ee-bffa-00155de90539");
    }

    private Integer determineOrderOrganization(SupplierOrderEvent event) {
        return supplierRepository.findOrganizationIdById(Long.valueOf(event.getNewEntity().getSupplierId()))
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID: " + event.getNewEntity().getSupplierId() + " not found"));
    }

    private Integer determineOrderOrganization(ClientOrderEvent event) {
        return clientRepository.findOrganizationIdById(Long.valueOf(event.getNewEntity().getClientId()))
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + event.getNewEntity().getClientId() + " not found"));
    }

    private boolean hasPermissions(FeaturePermissions featurePermissions, KafkaEvent.EventType eventType) {
        return switch (eventType) {
            case CREATE -> Boolean.TRUE.equals(featurePermissions.getCanCreate());
            case UPDATE -> Boolean.TRUE.equals(featurePermissions.getCanUpdate());
            case DELETE -> Boolean.TRUE.equals(featurePermissions.getCanDelete());
        };

    }
}

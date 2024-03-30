package org.chainoptim.core.notifications.service;

import org.chainoptim.features.supplier.model.SupplierOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListenerServiceImpl implements NotificationListenerService {

    private final NotificationService notificationService;

    @Autowired
    public NotificationListenerServiceImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "supplier-order-events", groupId = "notification-group")
    public void listenSupplierOrderEvent(SupplierOrder supplierOrder) {
        notificationService.processEvent(supplierOrder);

    }
}

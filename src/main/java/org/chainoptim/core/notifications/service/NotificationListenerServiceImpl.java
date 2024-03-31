package org.chainoptim.core.notifications.service;

import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListenerServiceImpl implements NotificationListenerService {

    private final NotificationService notificationService;
    private final NotificationFormatterService notificationFormatterService;

    @Autowired
    public NotificationListenerServiceImpl(NotificationService notificationService,
                                           NotificationFormatterService notificationFormatterService) {
        this.notificationService = notificationService;
        this.notificationFormatterService = notificationFormatterService;
    }

    @KafkaListener(topics = "supplier-order-events", groupId = "notification-group")
    public void listenSupplierOrderEvent(SupplierOrderEvent event) {
        notificationService.createNotification(event);
    }

    @KafkaListener(topics = "client-order-events", groupId = "notification-group")
    public void listenClientOrderEvent(ClientOrderEvent event) {
        notificationService.createNotification(event);
    }
}

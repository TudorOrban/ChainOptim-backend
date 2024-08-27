package org.chainoptim.core.overview.notifications.service;

import org.chainoptim.features.demand.model.ClientOrderEvent;
import org.chainoptim.features.supply.model.SupplierOrderEvent;
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
    public void listenSupplierOrderEvent(SupplierOrderEvent event) {
        notificationService.createNotification(event);
    }

    @KafkaListener(topics = "client-order-events", groupId = "notification-group")
    public void listenClientOrderEvent(ClientOrderEvent event) {
        notificationService.createNotification(event);
    }
}

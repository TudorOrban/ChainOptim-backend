package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationFormatterServiceImpl implements NotificationFormatterService {

    public <T> Notification formatEvent(T event, String entityType) {
        System.out.println("Formatting event: " + event);
        Notification notification = new Notification();
        notification.setTitle("Event title");
        notification.setMessage(formatMessage(event, entityType));
        notification.setReadStatus(false);
        notification.setEntityType(entityType);

        return notification;
    }

    private <T> String formatMessage(T event, String entityType) {
        return switch (entityType) {
            case "Supplier Order" -> "A new Supplier Order has been created";
            case "Client Order" -> "A new Client Order has been created";
            default -> "";
        };
    }
}

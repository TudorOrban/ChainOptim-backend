package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationFormatterServiceImpl implements NotificationFormatterService {

    public Notification formatEvent(SupplierOrderEvent event) {
        System.out.println("Formatting event: " + event);
        Notification notification = new Notification();
        notification.setTitle(formatTitle(event.getEntityType()));
        notification.setMessage(formatMessage(event.getEntityType(), event.getEventType()));
        notification.setReadStatus(false);
        notification.setEntityType(event.getEntityType());

        return notification;
    }

    public Notification formatEvent(ClientOrderEvent event) {
        System.out.println("Formatting event: " + event);
        Notification notification = new Notification();
        notification.setTitle(formatTitle(event.getEntityType()));
        notification.setMessage(formatMessage(event.getEntityType(), event.getEventType()));
        notification.setReadStatus(false);
        notification.setEntityType(event.getEntityType());

        return notification;
    }

    private String formatMessage(String entityType, KafkaEvent.EventType eventType) {
        return switch (entityType) {
            case "Supplier Order" -> switch (eventType) {
                case KafkaEvent.EventType.CREATE -> "A new Supplier Order has been created";
                case KafkaEvent.EventType.UPDATE -> "A Supplier Order has been updated";
                case KafkaEvent.EventType.DELETE -> "A Supplier Order has been deleted";
            };
            case "Client Order" -> switch (eventType) {
                case KafkaEvent.EventType.CREATE -> "A new Client Order has been created";
                case KafkaEvent.EventType.UPDATE -> "A Client Order has been updated";
                case KafkaEvent.EventType.DELETE -> "A Client Order has been deleted";
            };
            default -> "";
        };
    }

    private String formatTitle(String entityType) {
        return switch (entityType) {
            case "Supplier Order" -> "Supplier Order";
            case "Client Order" -> "Client Order";
            default -> "";
        };
    }
}

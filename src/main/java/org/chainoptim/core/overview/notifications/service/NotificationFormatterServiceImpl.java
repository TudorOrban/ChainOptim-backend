package org.chainoptim.core.overview.notifications.service;

import org.chainoptim.core.overview.notifications.model.KafkaEvent;
import org.chainoptim.core.overview.notifications.model.Notification;
import org.chainoptim.features.demand.clientorder.model.ClientOrderEvent;
import org.chainoptim.features.supply.supplierorder.model.SupplierOrderEvent;
import org.chainoptim.shared.enums.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationFormatterServiceImpl implements NotificationFormatterService {

    private final ExtraInfoFormatterService extraInfoFormatterService;

    @Autowired
    public NotificationFormatterServiceImpl(ExtraInfoFormatterService extraInfoFormatterService) {
        this.extraInfoFormatterService = extraInfoFormatterService;
    }

    public Notification formatEvent(SupplierOrderEvent event) {
        System.out.println("Formatting event: " + event);
        Notification notification = new Notification();
        notification.setTitle(formatTitle(event.getEntityType(), event.getEventType()));
        notification.setMessage(formatMessage(event.getEntityType(), event.getEventType(), event.getMainEntityType(), event.getMainEntityName()));
        notification.setReadStatus(false);
        notification.setEntityType(event.getEntityType());
        notification.setExtraInfo(extraInfoFormatterService.formatExtraInfo(event));

        return notification;
    }

    public Notification formatEvent(ClientOrderEvent event) {
        System.out.println("Formatting event: " + event);
        Notification notification = new Notification();
        notification.setTitle(formatTitle(event.getEntityType(), event.getEventType()));
        notification.setMessage(formatMessage(event.getEntityType(), event.getEventType(), event.getMainEntityType(), event.getMainEntityName()));
        notification.setReadStatus(false);
        notification.setEntityType(event.getEntityType());

        return notification;
    }

    private String formatTitle(Feature entityType, KafkaEvent.EventType eventType) {
        return switch (entityType) {
            case SUPPLIER_ORDER -> switch (eventType) {
                case KafkaEvent.EventType.CREATE -> "Supplier Order Added";
                case KafkaEvent.EventType.UPDATE -> "Supplier Order Updated";
                case KafkaEvent.EventType.DELETE -> "Supplier Order Deleted";
            };
            case CLIENT_ORDER -> switch (eventType) {
                case KafkaEvent.EventType.CREATE -> "Client Order Added";
                case KafkaEvent.EventType.UPDATE -> "Client Order Updated";
                case KafkaEvent.EventType.DELETE -> "Client Order Deleted";
            };
            default -> "";
        };
    }

    private String formatMessage(Feature entityType, KafkaEvent.EventType eventType, Feature mainEntityType, String mainEntityName) {
        String message = switch (entityType) {
            case SUPPLIER_ORDER -> switch (eventType) {
                case KafkaEvent.EventType.CREATE -> "A new Supplier Order has been created";
                case KafkaEvent.EventType.UPDATE -> "A Supplier Order has been updated";
                case KafkaEvent.EventType.DELETE -> "A Supplier Order has been deleted";
            };
            case CLIENT_ORDER -> switch (eventType) {
                case KafkaEvent.EventType.CREATE -> "A new Client Order has been created";
                case KafkaEvent.EventType.UPDATE -> "A Client Order has been updated";
                case KafkaEvent.EventType.DELETE -> "A Client Order has been deleted";
            };
            default -> "";
        };

        if (mainEntityName != null) {
            message += " for " + mainEntityType.toString() + " " + mainEntityName;
        }

        return message;
    }
}

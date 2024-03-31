package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;

public interface NotificationFormatterService {

    Notification formatEvent(SupplierOrderEvent event);
    Notification formatEvent(ClientOrderEvent event);
}

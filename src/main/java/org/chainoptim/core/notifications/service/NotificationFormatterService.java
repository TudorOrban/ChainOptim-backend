package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.features.demand.model.ClientOrderEvent;
import org.chainoptim.features.supply.model.SupplierOrderEvent;

public interface NotificationFormatterService {

    Notification formatEvent(SupplierOrderEvent event);
    Notification formatEvent(ClientOrderEvent event);
}

package org.chainoptim.core.overview.notifications.service;

import org.chainoptim.core.overview.notifications.model.Notification;
import org.chainoptim.features.demand.clientorder.model.ClientOrderEvent;
import org.chainoptim.features.supply.supplierorder.model.SupplierOrderEvent;

public interface NotificationFormatterService {

    Notification formatEvent(SupplierOrderEvent event);
    Notification formatEvent(ClientOrderEvent event);
}

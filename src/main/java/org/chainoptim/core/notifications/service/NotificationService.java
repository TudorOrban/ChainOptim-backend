package org.chainoptim.core.notifications.service;

import org.chainoptim.features.demand.model.ClientOrderEvent;
import org.chainoptim.features.supply.model.SupplierOrderEvent;

public interface NotificationService {

    void createNotification(SupplierOrderEvent event);
    void createNotification(ClientOrderEvent event);
}

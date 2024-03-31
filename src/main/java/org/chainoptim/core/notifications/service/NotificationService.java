package org.chainoptim.core.notifications.service;

import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;

public interface NotificationService {

    void createNotification(SupplierOrderEvent event);
    void createNotification(ClientOrderEvent event);
}

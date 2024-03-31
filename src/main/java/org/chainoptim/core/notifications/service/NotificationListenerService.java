package org.chainoptim.core.notifications.service;

import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;

public interface NotificationListenerService {

    void listenSupplierOrderEvent(SupplierOrderEvent event);
    void listenClientOrderEvent(ClientOrderEvent event);
}

package org.chainoptim.core.overview.notifications.service;

import org.chainoptim.features.demand.clientorder.model.ClientOrderEvent;
import org.chainoptim.features.supply.model.SupplierOrderEvent;

public interface NotificationListenerService {

    void listenSupplierOrderEvent(SupplierOrderEvent event);
    void listenClientOrderEvent(ClientOrderEvent event);
}

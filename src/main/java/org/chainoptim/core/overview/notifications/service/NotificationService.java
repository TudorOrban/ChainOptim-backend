package org.chainoptim.core.overview.notifications.service;

import org.chainoptim.features.demand.clientorder.model.ClientOrderEvent;
import org.chainoptim.features.supply.supplierorder.model.SupplierOrderEvent;

public interface NotificationService {

    void createNotification(SupplierOrderEvent event);
    void createNotification(ClientOrderEvent event);
}

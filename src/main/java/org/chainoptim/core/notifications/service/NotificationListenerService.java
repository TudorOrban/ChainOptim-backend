package org.chainoptim.core.notifications.service;

import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.supplier.model.SupplierOrder;

public interface NotificationListenerService {

    void listenSupplierOrderEvent(SupplierOrder supplierOrder);
    void listenClientOrderEvent(ClientOrder clientOrder);
}

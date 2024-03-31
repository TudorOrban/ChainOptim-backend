package org.chainoptim.core.notifications.service;

import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;

import java.util.List;

public interface NotificationDistributionService {

    List<String> distributeEventToUsers(SupplierOrderEvent event);
    List<String> distributeEventToUsers(ClientOrderEvent event);
}

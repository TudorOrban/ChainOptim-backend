package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.NotificationUserDistribution;
import org.chainoptim.features.demand.model.ClientOrderEvent;
import org.chainoptim.features.supply.model.SupplierOrderEvent;

public interface NotificationDistributionService {

    NotificationUserDistribution distributeEventToUsers(SupplierOrderEvent event);
    NotificationUserDistribution distributeEventToUsers(ClientOrderEvent event);
}

package org.chainoptim.core.overview.notifications.service;

import org.chainoptim.core.overview.notifications.model.NotificationUserDistribution;
import org.chainoptim.features.demand.clientorder.model.ClientOrderEvent;
import org.chainoptim.features.supply.model.SupplierOrderEvent;

public interface NotificationDistributionService {

    NotificationUserDistribution distributeEventToUsers(SupplierOrderEvent event);
    NotificationUserDistribution distributeEventToUsers(ClientOrderEvent event);
}

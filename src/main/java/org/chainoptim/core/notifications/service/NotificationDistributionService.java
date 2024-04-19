package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.NotificationUserDistribution;
import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;

import org.springframework.data.util.Pair;

import java.util.List;

public interface NotificationDistributionService {

    NotificationUserDistribution distributeEventToUsers(SupplierOrderEvent event);
    NotificationUserDistribution distributeEventToUsers(ClientOrderEvent event);
}

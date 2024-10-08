package org.chainoptim.core.overview.notifications.service;

import org.chainoptim.core.overview.notifications.model.NotificationExtraInfo;
import org.chainoptim.features.supply.supplierorder.model.SupplierOrderEvent;

public interface ExtraInfoFormatterService {

    NotificationExtraInfo formatExtraInfo(SupplierOrderEvent orderEvent);
}

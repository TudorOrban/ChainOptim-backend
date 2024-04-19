package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.NotificationExtraInfo;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;

public interface ExtraInfoFormatterService {

    NotificationExtraInfo formatExtraInfo(SupplierOrderEvent orderEvent);
}

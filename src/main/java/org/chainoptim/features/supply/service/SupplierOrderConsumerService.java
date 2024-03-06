package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.SupplierOrder;

public interface SupplierOrderConsumerService {
    void listenSupplierOrderEvent(SupplierOrder order);
}

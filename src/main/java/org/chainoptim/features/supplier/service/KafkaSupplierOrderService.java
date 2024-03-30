package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.model.SupplierOrder;

public interface KafkaSupplierOrderService {

    void sendSupplierOrder(SupplierOrder order);
}

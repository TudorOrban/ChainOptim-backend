package org.chainoptim.features.supply.supplierorder.service;

import org.chainoptim.features.supply.supplierorder.model.SupplierOrderEvent;

import java.util.List;

public interface KafkaSupplierOrderService {

    void sendSupplierOrderEvent(SupplierOrderEvent orderEvent);
    void sendSupplierOrderEventsInBulk(List<SupplierOrderEvent> kafkaEvents);
}

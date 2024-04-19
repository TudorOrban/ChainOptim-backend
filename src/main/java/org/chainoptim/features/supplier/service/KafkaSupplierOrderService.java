package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.model.SupplierOrderEvent;

import java.util.List;

public interface KafkaSupplierOrderService {

    void sendSupplierOrderEvent(SupplierOrderEvent orderEvent);
    void sendSupplierOrderEventsInBulk(List<SupplierOrderEvent> kafkaEvents);
}

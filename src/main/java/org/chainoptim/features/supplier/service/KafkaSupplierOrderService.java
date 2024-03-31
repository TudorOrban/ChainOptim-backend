package org.chainoptim.features.supplier.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.features.supplier.model.SupplierOrder;

import java.util.List;

public interface KafkaSupplierOrderService {

    void sendSupplierOrderEvent(SupplierOrder orderEvent, KafkaEvent.EventType eventType);
    void sendSupplierOrderEventsInBulk(List<SupplierOrder> orderEvents, KafkaEvent.EventType eventType);
}

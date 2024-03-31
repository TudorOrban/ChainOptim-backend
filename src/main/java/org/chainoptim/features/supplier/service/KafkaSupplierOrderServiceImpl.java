package org.chainoptim.features.supplier.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaSupplierOrderServiceImpl implements KafkaSupplierOrderService {

    private final KafkaTemplate<String, SupplierOrderEvent> kafkaTemplate;

    @Autowired
    public KafkaSupplierOrderServiceImpl(KafkaTemplate<String, SupplierOrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${supplier.order.topic.name:supplier-order-events}")
    private String supplierOrderTopicName;

    public void sendSupplierOrderEvent(SupplierOrder order, KafkaEvent.EventType eventType) {
        SupplierOrderEvent orderEvent = convertToEvent(order, eventType);
        kafkaTemplate.send(supplierOrderTopicName, orderEvent);
    }

    public void sendSupplierOrderEventsInBulk(List<SupplierOrder> orders, KafkaEvent.EventType eventType) {
        orders.stream()
                .map(order -> convertToEvent(order, eventType))
                .forEach(orderEvent -> kafkaTemplate.send(supplierOrderTopicName, orderEvent));
    }

    private SupplierOrderEvent convertToEvent(SupplierOrder order, KafkaEvent.EventType eventType) {
        SupplierOrderEvent event = new SupplierOrderEvent();
        event.setNewEntity(order);
        event.setEntityType("Supplier Order");
        event.setEventType(eventType);
        return event;
    }
}

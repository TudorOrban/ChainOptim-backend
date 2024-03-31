package org.chainoptim.features.client.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.client.model.ClientOrderEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaClientOrderServiceImpl implements KafkaClientOrderService {

    private final KafkaTemplate<String, ClientOrderEvent> kafkaTemplate;

    @Autowired
    public KafkaClientOrderServiceImpl(KafkaTemplate<String, ClientOrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${client.order.topic.name:client-order-events}")
    private String supplierOrderTopicName;

    public void sendClientOrderEvent(ClientOrder order, KafkaEvent.EventType eventType) {
        ClientOrderEvent orderEvent = convertToEvent(order, eventType);
        kafkaTemplate.send(supplierOrderTopicName, orderEvent);
    }

    public void sendClientOrderEventsInBulk(List<ClientOrder> orders, KafkaEvent.EventType eventType) {
        orders.stream()
                .map(order -> convertToEvent(order, eventType))
                .forEach(orderEvent -> kafkaTemplate.send(supplierOrderTopicName, orderEvent));
    }

    private ClientOrderEvent convertToEvent(ClientOrder order, KafkaEvent.EventType eventType) {
        ClientOrderEvent event = new ClientOrderEvent();
        event.setNewEntity(order);
        event.setEntityType("Client Order");
        event.setEventType(eventType);
        return event;
    }
}

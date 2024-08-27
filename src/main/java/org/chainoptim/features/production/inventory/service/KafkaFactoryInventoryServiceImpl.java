package org.chainoptim.features.production.inventory.service;

import org.chainoptim.features.production.inventory.model.FactoryInventoryItemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaFactoryInventoryServiceImpl implements KafkaFactoryInventoryService {

    private final KafkaTemplate<String, FactoryInventoryItemEvent> kafkaTemplate;

    @Autowired
    public KafkaFactoryInventoryServiceImpl(KafkaTemplate<String, FactoryInventoryItemEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${client.order.topic.name:factory-inventory-item-events}")
    private String factoryInventoryItemTopicName;

    public void sendFactoryInventoryItemEvent(FactoryInventoryItemEvent orderEvent) {
        kafkaTemplate.send(factoryInventoryItemTopicName, orderEvent);
    }

    public void sendFactoryInventoryItemEventsInBulk(List<FactoryInventoryItemEvent> kafkaEvents) {
        kafkaEvents
                .forEach(orderEvent -> kafkaTemplate.send(factoryInventoryItemTopicName, orderEvent));
    }
}

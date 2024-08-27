package org.chainoptim.features.demand.service;

import org.chainoptim.features.demand.model.ClientOrderEvent;

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
    private String clientOrderTopicName;

    public void sendClientOrderEvent(ClientOrderEvent orderEvent) {
        kafkaTemplate.send(clientOrderTopicName, orderEvent);
    }

    public void sendClientOrderEventsInBulk(List<ClientOrderEvent> kafkaEvents) {
        kafkaEvents
                .forEach(orderEvent -> kafkaTemplate.send(clientOrderTopicName, orderEvent));
    }
}

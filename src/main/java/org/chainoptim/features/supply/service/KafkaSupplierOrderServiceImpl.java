package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.SupplierOrderEvent;
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

    public void sendSupplierOrderEvent(SupplierOrderEvent orderEvent) {
        kafkaTemplate.send(supplierOrderTopicName, orderEvent);
    }

    public void sendSupplierOrderEventsInBulk(List<SupplierOrderEvent> kafkaEvents) {
        kafkaEvents
                .forEach(orderEvent -> kafkaTemplate.send(supplierOrderTopicName, orderEvent));
    }
}

package org.chainoptim.features.client.service;

import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.service.KafkaSupplierOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaClientOrderServiceImpl implements KafkaClientOrderService {

    private final KafkaTemplate<String, ClientOrder> kafkaTemplate;

    @Autowired
    public KafkaClientOrderServiceImpl(KafkaTemplate<String, ClientOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${client.order.topic.name:client-order-events}")
    private String supplierOrderTopicName;

    public void sendClientOrder(ClientOrder order) {
        kafkaTemplate.send(supplierOrderTopicName, order);
    }
}

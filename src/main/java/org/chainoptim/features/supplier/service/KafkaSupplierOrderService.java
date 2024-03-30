package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.model.SupplierOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSupplierOrderService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSupplierOrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${supplier.order.topic.name:supplier-order-events}")
    private String supplierOrderTopicName;

    public void sendSupplierOrder(String order) {
        kafkaTemplate.send(supplierOrderTopicName, "Supplier order message");
    }
}

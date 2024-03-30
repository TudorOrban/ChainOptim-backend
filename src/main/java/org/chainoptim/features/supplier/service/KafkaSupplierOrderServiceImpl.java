package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.model.SupplierOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSupplierOrderServiceImpl implements KafkaSupplierOrderService {

    private final KafkaTemplate<String, SupplierOrder> kafkaTemplate;

    @Autowired
    public KafkaSupplierOrderServiceImpl(KafkaTemplate<String, SupplierOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${supplier.order.topic.name:supplier-order-events}")
    private String supplierOrderTopicName;

    public void sendSupplierOrder(SupplierOrder order) {
        kafkaTemplate.send(supplierOrderTopicName, order);
    }
}

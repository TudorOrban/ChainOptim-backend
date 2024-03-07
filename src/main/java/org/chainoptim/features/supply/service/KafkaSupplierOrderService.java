package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.SupplierOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSupplierOrderService {

    @Autowired
    private KafkaTemplate<String, SupplierOrder> kafkaTemplate;

    @Value("${supplier.order.topic.name:supplier-order-events}")
    private String supplierOrderTopicName;

    public void sendSupplierOrder(SupplierOrder order) {
        kafkaTemplate.send(supplierOrderTopicName, order);
    }
}

package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.SupplierOrder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SupplierOrderConsumerServiceImpl implements SupplierOrderConsumerService {

    @KafkaListener(topics = "supplier-order-events", groupId = "supplier-order-consumer")
    public void listenSupplierOrderEvent(SupplierOrder order) {
        System.out.println("Received Supplier Order: " + order);
    }
}

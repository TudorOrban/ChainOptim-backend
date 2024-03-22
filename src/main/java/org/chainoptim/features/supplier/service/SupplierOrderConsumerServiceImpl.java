package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.model.SupplierOrder;
import org.springframework.stereotype.Service;

@Service
public class SupplierOrderConsumerServiceImpl implements SupplierOrderConsumerService {

//    @KafkaListener(topics = "supplier-order-events", groupId = "supplier-order-consumer")
    public void listenSupplierOrderEvent(SupplierOrder order) {
        System.out.println("Received Supplier Order: " + order);
    }
}

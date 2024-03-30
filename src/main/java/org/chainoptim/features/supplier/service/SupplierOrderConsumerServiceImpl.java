package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.model.SupplierOrder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Component
public class SupplierOrderConsumerServiceImpl implements SupplierOrderConsumerService {

    private final Logger logger = Logger.getLogger(SupplierOrderConsumerServiceImpl.class.getName());

    @KafkaListener(topics = "supplier-order-events", groupId = "supplier-order-consumer")
    public void listenSupplierOrderEvent(SupplierOrder supplierOrder) {
        logger.info("Received Kafka message: " + supplierOrder);
    }
}

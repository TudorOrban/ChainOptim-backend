package org.chainoptim.features.client.service;

import org.chainoptim.features.client.model.ClientOrder;

public interface KafkaClientOrderService {

    void sendClientOrder(ClientOrder order);
}

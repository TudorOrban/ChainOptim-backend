package org.chainoptim.features.client.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.features.client.model.ClientOrder;

import java.util.List;

public interface KafkaClientOrderService {

    void sendClientOrderEvent(ClientOrder order, KafkaEvent.EventType eventType);
    void sendClientOrderEventsInBulk(List<ClientOrder> orders, KafkaEvent.EventType eventType);
}

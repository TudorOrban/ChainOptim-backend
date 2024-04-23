package org.chainoptim.features.client.service;

import org.chainoptim.features.client.model.ClientOrderEvent;

import java.util.List;

public interface KafkaClientOrderService {

    void sendClientOrderEvent(ClientOrderEvent orderEvent);
    void sendClientOrderEventsInBulk(List<ClientOrderEvent> orderEvents);
}

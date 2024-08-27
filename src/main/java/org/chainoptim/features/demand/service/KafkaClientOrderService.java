package org.chainoptim.features.demand.service;

import org.chainoptim.features.demand.model.ClientOrderEvent;

import java.util.List;

public interface KafkaClientOrderService {

    void sendClientOrderEvent(ClientOrderEvent orderEvent);
    void sendClientOrderEventsInBulk(List<ClientOrderEvent> orderEvents);
}

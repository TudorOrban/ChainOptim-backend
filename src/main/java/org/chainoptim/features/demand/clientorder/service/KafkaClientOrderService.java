package org.chainoptim.features.demand.clientorder.service;

import org.chainoptim.features.demand.clientorder.model.ClientOrderEvent;

import java.util.List;

public interface KafkaClientOrderService {

    void sendClientOrderEvent(ClientOrderEvent orderEvent);
    void sendClientOrderEventsInBulk(List<ClientOrderEvent> orderEvents);
}

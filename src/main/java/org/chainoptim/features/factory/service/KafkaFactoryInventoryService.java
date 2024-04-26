package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.model.FactoryInventoryItemEvent;

import java.util.List;

public interface KafkaFactoryInventoryService {

    void sendFactoryInventoryItemEvent(FactoryInventoryItemEvent itemEvent);
    void sendFactoryInventoryItemEventsInBulk(List<FactoryInventoryItemEvent> itemEvents);
}

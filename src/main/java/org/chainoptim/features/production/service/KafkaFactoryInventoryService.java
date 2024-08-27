package org.chainoptim.features.production.service;

import org.chainoptim.features.production.model.FactoryInventoryItemEvent;

import java.util.List;

public interface KafkaFactoryInventoryService {

    void sendFactoryInventoryItemEvent(FactoryInventoryItemEvent itemEvent);
    void sendFactoryInventoryItemEventsInBulk(List<FactoryInventoryItemEvent> itemEvents);
}

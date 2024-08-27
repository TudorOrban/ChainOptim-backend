package org.chainoptim.features.production.inventory.service;

import org.chainoptim.features.production.inventory.model.FactoryInventoryItemEvent;

import java.util.List;

public interface KafkaFactoryInventoryService {

    void sendFactoryInventoryItemEvent(FactoryInventoryItemEvent itemEvent);
    void sendFactoryInventoryItemEventsInBulk(List<FactoryInventoryItemEvent> itemEvents);
}

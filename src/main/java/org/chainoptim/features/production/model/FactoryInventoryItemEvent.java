package org.chainoptim.features.production.model;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.shared.enums.Feature;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FactoryInventoryItemEvent extends KafkaEvent<FactoryInventoryItem> {

    public FactoryInventoryItemEvent(FactoryInventoryItem newEntity, FactoryInventoryItem oldEntity, EventType eventType, Integer mainEntityId, Feature mainEntityType, String mainEntityName) {
        super(newEntity, oldEntity, Feature.FACTORY_INVENTORY, eventType, mainEntityId, mainEntityType, mainEntityName);
    }
}

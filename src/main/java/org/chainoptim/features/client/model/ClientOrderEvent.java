package org.chainoptim.features.client.model;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.shared.enums.Feature;

public class ClientOrderEvent extends KafkaEvent<ClientOrder> {

    public ClientOrderEvent(ClientOrder newEntity, ClientOrder oldEntity, EventType eventType, Integer mainEntityId, Feature mainEntityType, String mainEntityName) {
        super(newEntity, oldEntity, Feature.SUPPLIER_ORDER, eventType, mainEntityId, mainEntityType, mainEntityName);
    }
}
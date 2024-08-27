package org.chainoptim.features.demand.clientorder.model;

import org.chainoptim.core.overview.notifications.model.KafkaEvent;
import org.chainoptim.shared.enums.Feature;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientOrderEvent extends KafkaEvent<ClientOrder> {

    public ClientOrderEvent(ClientOrder newEntity, ClientOrder oldEntity, EventType eventType, Integer mainEntityId, Feature mainEntityType, String mainEntityName) {
        super(newEntity, oldEntity, Feature.CLIENT_ORDER, eventType, mainEntityId, mainEntityType, mainEntityName);
    }
}
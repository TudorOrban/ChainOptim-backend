package org.chainoptim.features.supply.supplierorder.model;

import org.chainoptim.core.overview.notifications.model.KafkaEvent;
import org.chainoptim.shared.enums.Feature;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SupplierOrderEvent extends KafkaEvent<SupplierOrder> {

    public SupplierOrderEvent(SupplierOrder newEntity, SupplierOrder oldEntity, EventType eventType, Integer mainEntityId, Feature mainEntityType, String mainEntityName) {
        super(newEntity, oldEntity, Feature.SUPPLIER_ORDER, eventType, mainEntityId, mainEntityType, mainEntityName);
    }
}

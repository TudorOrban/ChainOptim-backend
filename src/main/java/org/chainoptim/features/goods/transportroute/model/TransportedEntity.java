package org.chainoptim.features.goods.transportroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportedEntity {

    private Integer entityId;
    private TransportedEntityType entityType;
    private String entityName;
    private Integer quantity;
    private Integer deliveredQuantity;
}

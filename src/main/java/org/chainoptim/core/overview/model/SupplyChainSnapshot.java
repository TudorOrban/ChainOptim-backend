package org.chainoptim.core.overview.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplyChainSnapshot {

    private final long membersCount;
    private final long productCount;
    private final long factoryCount;
    private final long warehouseCount;
    private final long suppliersCount;
    private final long clientsCount;
}

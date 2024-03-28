package org.chainoptim.core.overview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyChainSnapshot {

    private long membersCount;
    private long productsCount;
    private long factoriesCount;
    private long warehousesCount;
    private long suppliersCount;
    private long clientsCount;
}

package org.chainoptim.core.scsnapshot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Snapshot {

    private long membersCount;
    private long productsCount;
    private long componentsCount;
    private long factoriesCount;
    private long factoryInventoryItemsCount;
    private long warehousesCount;
    private long warehouseInventoryItemsCount;
    private long suppliersCount;
    private long supplierOrdersCount;
    private long clientsCount;
    private long clientOrdersCount;
}

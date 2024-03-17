package org.chainoptim.features.scanalysis.production.resourceallocation.model;

import lombok.Data;

@Data
public class DeficitResolution {

    private Integer neededComponentId;
    private Integer warehouseId;
    private Integer supplierOrderId;
    private Integer orderShipmentId;
    private Float neededQuantity;
    private Float suppliedQuantity;
    private Float suppliedArrivalTime;
}

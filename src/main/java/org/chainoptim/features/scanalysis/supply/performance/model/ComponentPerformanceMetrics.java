package org.chainoptim.features.scanalysis.supply.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentPerformanceMetrics {

    private int totalDeliveredOrders;
    private float totalDelays;
    private float ratioOfOnTimeDeliveries;
    private float ratioOfOnTimeShipments;
    private float averageShipmentsPerOrder;
    private float averageTimeToShipOrder;
}

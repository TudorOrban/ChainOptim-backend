package org.chainoptim.features.scanalysis.supply.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierPerformanceReport { // All time periods in days

    private int totalDeliveredOrders;

    // Timeliness
    private float totalDelays;
    private float averageDelayPerOrder;
    private float ratioOfOnTimeOrderDeliveries;
    private float averageDelayPerShipment;
    private float ratioOfOnTimeShipmentDeliveries;
    private float averageShipmentsPerOrder;
    private float averageTimeToShipOrder;

    // Quantity
    private Map<Integer, ComponentDeliveryPerformance> componentPerformances;


}

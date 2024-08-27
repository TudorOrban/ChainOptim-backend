package org.chainoptim.features.supply.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierPerformanceReport { // All time periods in days

    private Float overallScore;
    private Float timelinessScore;
    private Float quantityPerTimeScore;
    private Float availabilityScore;
    private Float qualityScore;

    private int totalDeliveredOrders;
    private float totalDelays;
    private float averageDelayPerOrder;
    private float ratioOfOnTimeOrderDeliveries;
    private float averageDelayPerShipment;
    private float ratioOfOnTimeShipmentDeliveries;
    private float averageShipmentsPerOrder;
    private float averageTimeToShipOrder;

    // Quantity
    private Map<Integer, ComponentDeliveryPerformance> componentPerformances = new HashMap<>();
}

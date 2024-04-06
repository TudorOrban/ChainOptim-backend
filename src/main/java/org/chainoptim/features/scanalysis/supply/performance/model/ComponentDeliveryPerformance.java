package org.chainoptim.features.scanalysis.supply.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentDeliveryPerformance {

    private int componentId;
    private float totalDeliveredOrders;
    private float totalDeliveredQuantity;
    private float averageDeliveredQuantity;
    private float averageOrderQuantity;
    private float averageShipmentQuantity;
    private float deliveredPerOrderedRatio;
    private LocalDateTime firstDeliveryDate;
    private Map<Float, Float> deliveredQuantityOverTime;
}

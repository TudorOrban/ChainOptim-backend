package org.chainoptim.features.scanalysis.production.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryStagePerformanceReport {

    private Integer factoryStageId;
    private Float capacity;
    private Float duration;

    private Float totalExecutedStages;
    private Float totalTimeDays;
    private Float averageExecutedCapacityPerDay;
    private Float minimumExecutedCapacityPerDay;

    private Float daysUnderCapacityPercentage; // Ignoring days not recorded in Production History

    private Map<Integer, Float> totalDeficits = new HashMap<>(); // Key: componentId
    private Map<Integer, Float> averageDeficits = new HashMap<>(); // Key: componentId

    private Float errorRate;

    private Float overallScore; // Computed based on the other scores
    private Float resourceReadinessScore; // Measures how many resources are available when needed
    private Float resourceUtilizationScore; // Measures lack of errors and resource waste

}

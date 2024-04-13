package org.chainoptim.features.scanalysis.production.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Map<Integer, Float> totalDeficits; // Key: componentId
    private Map<Integer, Float> averageDeficits; // Key: componentId


    private Float overallScore;
    private Float resourceDistributionScore;
    private Float resourceReadinessScore;
    private Float resourceUtilizationScore;


}

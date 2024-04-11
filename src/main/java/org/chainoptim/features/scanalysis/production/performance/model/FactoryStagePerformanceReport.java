package org.chainoptim.features.scanalysis.production.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryStagePerformanceReport {

    private Integer factoryStageId;
    private Float capacity;
    private Float averageExecutedCapacity;
    private Float totalExecutedStages;



    private Float overallScore;
    private Float resourceDistributionScore;
    private Float resourceReadinessScore;
    private Float resourceUtilizationScore;


}

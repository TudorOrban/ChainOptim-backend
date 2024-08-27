package org.chainoptim.features.production.analysis.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryPerformanceReport {

    private Float overallScore;
    private Float resourceDistributionScore; // Measures distribution of resources among stages
    private Float resourceReadinessScore;
    private Float resourceUtilizationScore;

    private Map<Integer, FactoryStagePerformanceReport> stageReports = new HashMap<>(); // Key: factoryStageId
}

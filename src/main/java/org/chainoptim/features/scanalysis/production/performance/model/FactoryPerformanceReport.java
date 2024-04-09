package org.chainoptim.features.scanalysis.production.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactoryPerformanceReport {

    private Map<Float, Float> deficits;
    private Map<Integer, FactoryStagePerformanceReport> stageReports; // Key: factoryStageId


}

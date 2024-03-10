package org.chainoptim.features.factory.model;

import lombok.Data;

import java.util.Map;

@Data
public class FactoryEvaluationReport {

    private Map<Integer, StageReport> stagesReport;

    @Data
    public static class StageReport {
        Integer id;
    }
}

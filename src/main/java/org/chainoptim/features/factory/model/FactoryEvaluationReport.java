package org.chainoptim.features.factory.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FactoryEvaluationReport {

    private List<StageReport> stageReports = new ArrayList<>();
    private String overallRecommendation;

    @Data
    public static class StageReport {
        private Integer stageId;
        private String stageName;
        private List<InputReport> inputReports = new ArrayList<>();
        private Float capacityUtilizationPercentage;
        private String stageRecommendation;
    }

    @Data
    public static class InputReport {
        private Integer componentId;
        private String componentName;
        private Float surplusComponentQuantity;
        private Float surplusComponentRatio;
        private String inputRecommendation;
    }
}

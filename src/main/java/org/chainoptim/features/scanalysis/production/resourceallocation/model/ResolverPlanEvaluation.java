package org.chainoptim.features.scanalysis.production.resourceallocation.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResolverPlanEvaluation {
    private Map<Integer, List<ResolutionEvaluation>> deficitEvaluations; // Key: componentId, Value: evaluations of component resolutions
    private Float overallScore;
}

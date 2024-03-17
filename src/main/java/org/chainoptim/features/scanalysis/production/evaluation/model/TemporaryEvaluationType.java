package org.chainoptim.features.scanalysis.production.evaluation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.DeficitResolverPlan;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResolutionEvaluation;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResolverPlanEvaluation;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TemporaryEvaluationType {
    private AllocationPlan allocationPlan;
    private DeficitResolverPlan resolverPlan;
    private ResolverPlanEvaluation resolverPlanEvaluation;
    private Map<Integer, List<ResolutionEvaluation>> recommendedResolutions;
}

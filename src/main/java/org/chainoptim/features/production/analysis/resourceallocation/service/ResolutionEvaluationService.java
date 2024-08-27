package org.chainoptim.features.production.analysis.resourceallocation.service;

import org.chainoptim.features.production.analysis.resourceallocation.model.ResolverPlanEvaluation;
import org.chainoptim.features.production.analysis.resourceallocation.model.DeficitResolverPlan;

public interface ResolutionEvaluationService {

    ResolverPlanEvaluation evaluateResolverPlan(DeficitResolverPlan plan);
}

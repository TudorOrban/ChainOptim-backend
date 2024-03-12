package org.chainoptim.features.evaluation.production.resourceallocation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.DeficitResolverPlan;
import org.chainoptim.features.evaluation.production.resourceallocation.model.ResolverPlanEvaluation;

public interface ResolutionEvaluationService {

    ResolverPlanEvaluation evaluateResolverPlan(DeficitResolverPlan plan);
}

package org.chainoptim.features.scanalysis.production.resourceallocation.service;

import org.chainoptim.features.scanalysis.production.resourceallocation.model.DeficitResolverPlan;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResolverPlanEvaluation;

public interface ResolutionEvaluationService {

    ResolverPlanEvaluation evaluateResolverPlan(DeficitResolverPlan plan);
}

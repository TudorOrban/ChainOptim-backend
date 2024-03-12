package org.chainoptim.features.evaluation.production.evaluation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.evaluation.production.resourceallocation.model.DeficitResolverPlan;
import org.springframework.data.util.Pair;

public interface FactoryGraphEvaluationService {
    Pair<AllocationPlan, DeficitResolverPlan> evaluateFactory(Integer factoryId, Float duration);
    void analyzeFactoryGraphWithPipelines(Integer factoryId, Float duration);
}

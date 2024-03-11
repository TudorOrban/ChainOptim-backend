package org.chainoptim.features.evaluation.production.evaluation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.AllocationPlan;

public interface FactoryGraphEvaluationService {
    AllocationPlan evaluateFactory(Integer factoryId, Float duration);
    void analyzeFactoryGraphWithPipelines(Integer factoryId, Float duration);
}

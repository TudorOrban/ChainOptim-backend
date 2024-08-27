package org.chainoptim.features.production.analysis.evaluation.service;

import org.chainoptim.features.production.analysis.evaluation.model.TemporaryEvaluationType;

public interface FactoryGraphEvaluationService {
    TemporaryEvaluationType evaluateFactory(Integer factoryId, Float duration);
    void analyzeFactoryGraphWithPipelines(Integer factoryId, Float duration);
}

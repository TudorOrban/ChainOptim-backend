package org.chainoptim.features.scanalysis.production.evaluation.service;

import org.chainoptim.features.scanalysis.production.evaluation.model.TemporaryEvaluationType;

public interface FactoryGraphEvaluationService {
    TemporaryEvaluationType evaluateFactory(Integer factoryId, Float duration);
    void analyzeFactoryGraphWithPipelines(Integer factoryId, Float duration);
}

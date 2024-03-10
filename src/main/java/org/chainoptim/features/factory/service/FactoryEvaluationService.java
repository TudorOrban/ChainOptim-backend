package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.model.FactoryEvaluationReport;

public interface FactoryEvaluationService {
    FactoryEvaluationReport evaluateFactory(Integer factoryId);
}

package org.chainoptim.features.factory.service;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.factory.model.FactoryEvaluationReport;

import java.util.List;

public interface FactoryEvaluationService {
    FactoryEvaluationReport evaluateFactory(Integer factoryId);
    FactoryGraph getFactoryPipelineGraph(Integer factoryId);
}

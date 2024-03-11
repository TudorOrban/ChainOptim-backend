package org.chainoptim.features.evaluation.production.recommendation.service;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.recommendation.model.FactoryRecommendationReport;

public interface FactoryRecommendationService {
    FactoryRecommendationReport evaluateFactory(Integer factoryId);
    FactoryGraph getFactoryPipelineGraph(Integer factoryId);
}

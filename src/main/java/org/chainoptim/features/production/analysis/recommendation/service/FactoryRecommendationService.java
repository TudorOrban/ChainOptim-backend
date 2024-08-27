package org.chainoptim.features.production.analysis.recommendation.service;

import org.chainoptim.features.production.analysis.recommendation.model.FactoryRecommendationReport;
import org.chainoptim.features.production.factorygraph.model.FactoryGraph;

public interface FactoryRecommendationService {
    FactoryRecommendationReport evaluateFactory(Integer factoryId);
    FactoryGraph getFactoryPipelineGraph(Integer factoryId);
}

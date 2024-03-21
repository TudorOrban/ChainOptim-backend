package org.chainoptim.features.scanalysis.production.recommendation.service;

import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.recommendation.model.FactoryRecommendationReport;

public interface FactoryRecommendationService {
    FactoryRecommendationReport evaluateFactory(Integer factoryId);
    FactoryGraph getFactoryPipelineGraph(Integer factoryId);
}

package org.chainoptim.features.production.analysis.recommendation.service;

import org.chainoptim.features.production.analysis.resourceallocation.model.ResolutionEvaluation;
import org.chainoptim.features.production.analysis.resourceallocation.model.ResolverPlanEvaluation;

import java.util.List;
import java.util.Map;

public interface FactoryResolutionRecommendationService {

    Map<Integer, List<ResolutionEvaluation>> recommendResolverPlanActions(ResolverPlanEvaluation planEvaluation);
    List<ResolutionEvaluation> recommendComponentResolution(List<ResolutionEvaluation> evaluations, Float neededQuantity);
}

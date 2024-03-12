package org.chainoptim.features.evaluation.production.recommendation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.ResolutionEvaluation;
import org.chainoptim.features.evaluation.production.resourceallocation.model.ResolverPlanEvaluation;

import java.util.List;
import java.util.Map;

public interface FactoryResolutionRecommendationService {

    Map<Integer, List<ResolutionEvaluation>> recommendResolverPlanActions(ResolverPlanEvaluation planEvaluation);
    List<ResolutionEvaluation> recommendComponentResolution(List<ResolutionEvaluation> evaluations, Float neededQuantity);
}

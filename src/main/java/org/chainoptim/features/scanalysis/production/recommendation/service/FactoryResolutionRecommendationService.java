package org.chainoptim.features.scanalysis.production.recommendation.service;

import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResolutionEvaluation;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResolverPlanEvaluation;

import java.util.List;
import java.util.Map;

public interface FactoryResolutionRecommendationService {

    Map<Integer, List<ResolutionEvaluation>> recommendResolverPlanActions(ResolverPlanEvaluation planEvaluation);
    List<ResolutionEvaluation> recommendComponentResolution(List<ResolutionEvaluation> evaluations, Float neededQuantity);
}

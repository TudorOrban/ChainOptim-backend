package org.chainoptim.features.evaluation.production.recommendation.service;

import org.chainoptim.features.evaluation.production.resourceallocation.model.ResolutionEvaluation;
import org.chainoptim.features.evaluation.production.resourceallocation.model.ResolverPlanEvaluation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FactoryResolutionRecommendationServiceImpl implements FactoryResolutionRecommendationService {

    public Map<Integer, List<ResolutionEvaluation>> recommendResolverPlanActions(ResolverPlanEvaluation planEvaluation) {
        Map<Integer, List<ResolutionEvaluation>> recommendations = new HashMap<>();

        for (Map.Entry<Integer, List<ResolutionEvaluation>> entry : planEvaluation.getDeficitEvaluations().entrySet()) {
            Integer componentId = entry.getKey();
            List<ResolutionEvaluation> evaluations = entry.getValue();
            Float neededQuantity = evaluations.getFirst().getResolution().getNeededQuantity(); // Get needed componentquantity from any resolution

            if (neededQuantity != null) {
                // Make recommendations for this component
                List<ResolutionEvaluation> componentRecommendations = recommendComponentResolution(evaluations, neededQuantity);
                recommendations.put(componentId, componentRecommendations);
            }
        }

        return recommendations;
    }

    public List<ResolutionEvaluation> recommendComponentResolution(List<ResolutionEvaluation> evaluations, Float neededQuantity) {
        List<ResolutionEvaluation> recommendedEvaluations = new ArrayList<>();

        // Sort by score
        evaluations.sort((e1, e2) -> Float.compare(e2.getScore(), e1.getScore()));

        // Add resolutions until deficit is covered
        for (ResolutionEvaluation evaluation : evaluations) {
            neededQuantity -= evaluation.getResolution().getSuppliedQuantity();

            if (neededQuantity > 0) {
                recommendedEvaluations.add(evaluation);
            } else {
                break;
            }
        }

        return recommendedEvaluations;
    }

}

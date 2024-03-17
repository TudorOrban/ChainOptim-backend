package org.chainoptim.features.scanalysis.production.resourceallocation.service;

import org.chainoptim.features.scanalysis.production.resourceallocation.model.DeficitResolution;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.DeficitResolverPlan;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResolutionEvaluation;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResolverPlanEvaluation;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResolutionEvaluationServiceImpl implements ResolutionEvaluationService {

    public ResolverPlanEvaluation evaluateResolverPlan(DeficitResolverPlan plan) {
        ResolverPlanEvaluation evaluation = new ResolverPlanEvaluation();

        // Group resolutions by componentId and evaluate them
        Map<Integer, List<ResolutionEvaluation>> evaluations = plan.getResolutions().stream()
                .sorted(Comparator.comparingInt(DeficitResolution::getNeededComponentId))
                .collect(Collectors.groupingBy(
                        DeficitResolution::getNeededComponentId,
                        Collectors.mapping(
                                resolution -> new ResolutionEvaluation(resolution, evaluateResolution(resolution)),
                                Collectors.toList())));

        evaluation.setDeficitEvaluations(evaluations);

        return evaluation;
    }

    public float evaluateResolution(DeficitResolution resolution) {
        float value = resolution.getSuppliedQuantity() / resolution.getNeededQuantity();
        if (resolution.getSuppliedArrivalTime() != null) {
            value = value / resolution.getSuppliedArrivalTime();
        }

        return value;
    }
}

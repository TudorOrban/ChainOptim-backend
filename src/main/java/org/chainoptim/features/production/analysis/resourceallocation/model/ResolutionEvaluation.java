package org.chainoptim.features.production.analysis.resourceallocation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResolutionEvaluation {
    private DeficitResolution resolution;
    private Float score;
}

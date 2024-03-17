package org.chainoptim.features.scanalysis.production.resourceallocation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResolutionEvaluation {
    private DeficitResolution resolution;
    private Float score;
}

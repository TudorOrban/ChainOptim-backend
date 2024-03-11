package org.chainoptim.features.evaluation.production.graph.model;

import lombok.Data;

@Data
public class SmallStageOutput {
    Integer id;
    Integer componentId;
    Float quantityPerStage;
    Float expectedOutputPerAllocation;
}
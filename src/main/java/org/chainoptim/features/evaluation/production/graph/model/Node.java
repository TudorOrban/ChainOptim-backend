package org.chainoptim.features.evaluation.production.graph.model;

import lombok.Data;

@Data
public class Node {
    SmallStage smallStage;
    Float numberOfStepsCapacity;
    Float perDuration;
    Integer priority;

}

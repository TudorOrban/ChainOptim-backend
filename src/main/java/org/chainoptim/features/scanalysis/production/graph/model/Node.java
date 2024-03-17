package org.chainoptim.features.scanalysis.production.graph.model;

import lombok.Data;

@Data
public class Node {
    SmallStage smallStage;
    Float numberOfStepsCapacity;
    Float perDuration;
    Float minimumRequiredCapacity;
    Integer priority;
    Float allocationCapacityRatio;
}

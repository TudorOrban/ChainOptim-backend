package org.chainoptim.features.scanalysis.production.factorygraph.model;

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

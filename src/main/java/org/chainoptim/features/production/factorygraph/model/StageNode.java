package org.chainoptim.features.production.factorygraph.model;

import lombok.Data;

@Data
public class StageNode {
    SmallStage smallStage;
    Float numberOfStepsCapacity;
    Float perDuration;
    Float minimumRequiredCapacity;
    Integer priority;
    Float allocationCapacityRatio;
}

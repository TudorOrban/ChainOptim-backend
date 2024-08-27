package org.chainoptim.features.production.analysis.resourceallocation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationResult {
    private Integer stageOutputId;
    private Integer factoryStageId;
    private String stageName;
    private Integer componentId;
    private String componentName;
    private Float resultedAmount;
    private Float fullAmount;
    private Float actualAmount;
}

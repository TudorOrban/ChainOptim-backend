package org.chainoptim.features.production.analysis.resourceallocation.model;

import lombok.Data;

@Data
public class ResourceAllocation {
    private Integer stageInputId;
    private Integer factoryStageId;
    private String stageName;
    private Integer componentId;
    private String componentName;
    private Integer allocatorInventoryItemId;
    private Float allocatedAmount;
    private Float requestedAmount;
    private Float actualAmount;
}

package org.chainoptim.features.scanalysis.production.resourceallocation.dto;

import org.chainoptim.features.scanalysis.production.resourceallocation.model.AllocationPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAllocationPlanDTO {

    private Integer factoryId;
    private AllocationPlan allocationPlan;
}

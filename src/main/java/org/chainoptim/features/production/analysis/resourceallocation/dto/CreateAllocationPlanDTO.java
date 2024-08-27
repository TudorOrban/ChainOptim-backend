package org.chainoptim.features.production.analysis.resourceallocation.dto;

import org.chainoptim.features.production.analysis.resourceallocation.model.AllocationPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAllocationPlanDTO {

    private Integer factoryId;
    private AllocationPlan allocationPlan;
    private LocalDateTime activationDate;
    private boolean isActive;
}

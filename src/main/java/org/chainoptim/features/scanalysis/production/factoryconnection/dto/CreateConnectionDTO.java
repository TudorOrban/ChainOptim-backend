package org.chainoptim.features.scanalysis.production.factoryconnection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConnectionDTO {

    private Integer factoryId;
    private Integer organizationId;
    private Integer srcFactoryStageId;
    private Integer srcStageOutputId;
    private Integer destFactoryStageId;
    private Integer destStageInputId;
}

package org.chainoptim.features.scanalysis.production.productconnection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConnectionDTO {

    private Integer productId;
    private Integer organizationId;
    Integer srcStageId;
    Integer srcStageOutputId;
    Integer destStageId;
    Integer destStageInputId;
}

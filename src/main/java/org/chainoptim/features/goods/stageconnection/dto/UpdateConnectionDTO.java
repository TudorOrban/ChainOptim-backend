package org.chainoptim.features.goods.stageconnection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateConnectionDTO {

    private Integer id;
    private Integer productId;
    private Integer organizationId;
    Integer srcStageId;
    Integer srcStageOutputId;
    Integer destStageId;
    Integer destStageInputId;
}
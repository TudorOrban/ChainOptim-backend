package org.chainoptim.features.productpipeline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStageInputDTO {

    private Integer id;
    private Integer productId;
    private Integer organizationId;
    private Integer stageId;
    private Integer componentId;
    private Float quantity;
}

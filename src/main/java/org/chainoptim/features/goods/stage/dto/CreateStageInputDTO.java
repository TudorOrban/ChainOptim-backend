package org.chainoptim.features.goods.stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStageInputDTO {

    private Integer productId;
    private Integer organizationId;
    private Integer stageId;
    private Integer componentId;
    private Float quantity;
}

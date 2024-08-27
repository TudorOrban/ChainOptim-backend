package org.chainoptim.features.goods.stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStageOutputDTO {

    private Integer id;
    private Integer organizationId;
    private Integer stageId;
    private Integer productId;
    private Integer outputProductId;
    private Integer componentId;
    private Float quantity;
}

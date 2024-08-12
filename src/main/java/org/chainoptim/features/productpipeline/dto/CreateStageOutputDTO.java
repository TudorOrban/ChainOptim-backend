package org.chainoptim.features.productpipeline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStageOutputDTO {

    private Integer organizationId;
    private Integer stageId;
    private Integer productId;
    private Integer outputProductId;
    private Integer componentId;
    private Float quantity;
}

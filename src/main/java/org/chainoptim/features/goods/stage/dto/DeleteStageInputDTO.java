package org.chainoptim.features.goods.stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteStageInputDTO {

    private Integer stageInputId;
    private Integer productId;
    private Integer organizationId;
}

package org.chainoptim.features.goods.stage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteStageOutputDTO {

    private Integer stageOutputId;
    private Integer productId;
    private Integer organizationId;
}

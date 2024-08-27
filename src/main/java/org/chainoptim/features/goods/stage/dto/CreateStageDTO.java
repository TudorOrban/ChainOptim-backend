package org.chainoptim.features.goods.stage.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStageDTO {
    private Integer productId;
    private Integer organizationId;
    private String name;
    private String description;
}

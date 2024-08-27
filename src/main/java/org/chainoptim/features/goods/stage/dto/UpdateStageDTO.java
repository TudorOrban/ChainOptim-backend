package org.chainoptim.features.goods.stage.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStageDTO {
    private Integer id;
    private String name;
    private String description;
}

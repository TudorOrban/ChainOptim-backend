package org.chainoptim.features.productpipeline.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStageDTO {
    private Integer id;
    private String name;
    private String description;
}

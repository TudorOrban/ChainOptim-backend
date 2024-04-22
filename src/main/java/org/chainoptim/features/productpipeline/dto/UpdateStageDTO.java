package org.chainoptim.features.productpipeline.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStageDTO {
    private Integer id;
    private String name;
    private String description;
}

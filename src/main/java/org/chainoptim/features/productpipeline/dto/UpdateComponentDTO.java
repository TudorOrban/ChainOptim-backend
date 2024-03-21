package org.chainoptim.features.productpipeline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateComponentDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer unitId;
}

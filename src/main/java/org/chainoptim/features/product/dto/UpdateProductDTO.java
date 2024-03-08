package org.chainoptim.features.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer unitId;
}

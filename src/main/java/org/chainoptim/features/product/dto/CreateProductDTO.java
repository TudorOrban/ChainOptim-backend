package org.chainoptim.features.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProductDTO {

    private String name;
    private String description;
    private Integer organizationId;
    private Integer unitId;
}

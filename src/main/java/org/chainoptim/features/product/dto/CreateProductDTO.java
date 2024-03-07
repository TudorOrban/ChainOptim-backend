package org.chainoptim.features.product.dto;

import lombok.Data;

@Data
public class CreateProductDTO {

    private String name;
    private String description;
    private Integer organizationId;
    private Integer unitId;
}

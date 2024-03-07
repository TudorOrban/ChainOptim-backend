package org.chainoptim.features.product.dto;

import lombok.Data;

@Data
public class UpdateProductDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer unitId;
}

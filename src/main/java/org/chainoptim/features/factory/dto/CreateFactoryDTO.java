package org.chainoptim.features.factory.dto;

import lombok.Data;

@Data
public class CreateFactoryDTO {

    private String name;
    private Integer organizationId;
    private Integer locationId;
}

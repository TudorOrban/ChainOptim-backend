package org.chainoptim.features.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCrateDTO {

    private String name;
    private Integer organizationId;
    private Integer componentId;
    private Float quantity;
    private Float volumeM3;
    private Boolean stackable;
    private Float heightM;
}

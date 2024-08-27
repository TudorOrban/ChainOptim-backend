package org.chainoptim.features.storage.crate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCrateDTO {

    private Integer id;
    private String name;
    private Integer componentId;
    private Float quantity;
    private Float volumeM3;
    private Boolean stackable;
    private Float heightM;
}

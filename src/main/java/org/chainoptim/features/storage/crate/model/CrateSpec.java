package org.chainoptim.features.storage.crate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrateSpec {

    private Integer crateId;
    private Integer componentId;
    private Float maxCrates;
}

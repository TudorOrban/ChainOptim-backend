package org.chainoptim.features.storage.compartment.model;

import org.chainoptim.features.storage.crate.model.CrateData;
import org.chainoptim.features.storage.crate.model.CrateSpec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompartmentData {

    private List<CrateSpec> crateSpecs;
    private List<CrateData> currentCrates;
}

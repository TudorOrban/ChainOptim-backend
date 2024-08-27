package org.chainoptim.features.storage.crate.service;

import org.chainoptim.features.storage.crate.dto.CreateCrateDTO;
import org.chainoptim.features.storage.crate.dto.UpdateCrateDTO;
import org.chainoptim.features.storage.crate.model.Crate;

import java.util.List;

public interface CrateService {

    List<Crate> getCratesByOrganizationId(Integer organizationId);
    Crate createCrate(CreateCrateDTO crateDTO);
    Crate updateCrate(UpdateCrateDTO crateDTO);
    void deleteCrate(Integer crateId);
}

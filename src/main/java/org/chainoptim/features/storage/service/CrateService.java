package org.chainoptim.features.storage.service;

import org.chainoptim.features.storage.dto.CreateCrateDTO;
import org.chainoptim.features.storage.dto.UpdateCrateDTO;
import org.chainoptim.features.storage.model.Crate;

import java.util.List;

public interface CrateService {

    List<Crate> getCratesByOrganizationId(Integer organizationId);
    Crate createCrate(CreateCrateDTO crateDTO);
    Crate updateCrate(UpdateCrateDTO crateDTO);
    void deleteCrate(Integer crateId);
}

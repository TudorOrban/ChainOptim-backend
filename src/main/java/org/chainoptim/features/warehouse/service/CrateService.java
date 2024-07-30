package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.dto.CreateCrateDTO;
import org.chainoptim.features.warehouse.dto.UpdateCrateDTO;
import org.chainoptim.features.warehouse.model.Crate;

import java.util.List;

public interface CrateService {

    List<Crate> getCratesByOrganizationId(Integer organizationId);
    Crate createCrate(CreateCrateDTO crateDTO);
    Crate updateCrate(UpdateCrateDTO crateDTO);
    void deleteCrate(Integer crateId);
}

package org.chainoptim.features.warehouse.dto;

import org.chainoptim.features.warehouse.model.Crate;

public class CrateDTOMapper {

    private CrateDTOMapper() {}

    public static Crate mapCreateCrateDTOToCrate(CreateCrateDTO createCrateDTO) {
        Crate crate = new Crate();
        crate.setName(createCrateDTO.getName());
        crate.setOrganizationId(createCrateDTO.getOrganizationId());
        crate.setComponentId(createCrateDTO.getComponentId());
        crate.setQuantity(createCrateDTO.getQuantity());
        crate.setVolumeM3(createCrateDTO.getVolumeM3());
        crate.setStackable(createCrateDTO.getStackable());
        crate.setHeightM(createCrateDTO.getHeightM());
        return crate;
    }

    public static Crate setUpdateCrateDTOToCrate(UpdateCrateDTO updateCrateDTO, Crate crate) {
        crate.setName(updateCrateDTO.getName());
        crate.setComponentId(updateCrateDTO.getComponentId());
        crate.setQuantity(updateCrateDTO.getQuantity());
        crate.setVolumeM3(updateCrateDTO.getVolumeM3());
        crate.setStackable(updateCrateDTO.getStackable());
        crate.setHeightM(updateCrateDTO.getHeightM());
        return crate;
    }
}

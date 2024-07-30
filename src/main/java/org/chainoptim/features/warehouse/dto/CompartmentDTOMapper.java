package org.chainoptim.features.warehouse.dto;

import org.chainoptim.features.warehouse.model.Compartment;

public class CompartmentDTOMapper {

    private CompartmentDTOMapper() {}

    public static Compartment mapCreateCompartmentDTOToCompartment(CreateCompartmentDTO createCompartmentDTO) {
        Compartment compartment = new Compartment();
        compartment.setName(createCompartmentDTO.getName());
        compartment.setWarehouseId(createCompartmentDTO.getWarehouseId());
        compartment.setOrganizationId(createCompartmentDTO.getOrganizationId());
        compartment.setData(createCompartmentDTO.getData());
        return compartment;
    }

    public static Compartment setUpdateCompartmentDTOToCompartment(UpdateCompartmentDTO updateCompartmentDTO, Compartment compartment) {
        compartment.setName(updateCompartmentDTO.getName());
        compartment.setData(updateCompartmentDTO.getData());
        return compartment;
    }
}

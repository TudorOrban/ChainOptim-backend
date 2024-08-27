package org.chainoptim.features.storage.service;

import org.chainoptim.features.storage.dto.CreateCompartmentDTO;
import org.chainoptim.features.storage.dto.UpdateCompartmentDTO;
import org.chainoptim.features.storage.model.Compartment;

import java.util.List;

public interface CompartmentService {

    List<Compartment> getCompartmentsByOrganizationId(Integer organizationId);
    List<Compartment> getCompartmentsByWarehouseId(Integer warehouseId);
    Compartment getCompartmentById(Integer compartmentId);
    Compartment createCompartment(CreateCompartmentDTO compartmentDTO);
    Compartment updateCompartment(UpdateCompartmentDTO compartmentDTO);
    void deleteCompartment(Integer compartmentId);
}

package org.chainoptim.features.storage.compartment.service;

import org.chainoptim.features.storage.compartment.dto.CreateCompartmentDTO;
import org.chainoptim.features.storage.compartment.dto.UpdateCompartmentDTO;
import org.chainoptim.features.storage.compartment.model.Compartment;

import java.util.List;

public interface CompartmentService {

    List<Compartment> getCompartmentsByOrganizationId(Integer organizationId);
    List<Compartment> getCompartmentsByWarehouseId(Integer warehouseId);
    Compartment getCompartmentById(Integer compartmentId);
    Compartment createCompartment(CreateCompartmentDTO compartmentDTO);
    Compartment updateCompartment(UpdateCompartmentDTO compartmentDTO);
    void deleteCompartment(Integer compartmentId);
}

package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.dto.CreateCompartmentDTO;
import org.chainoptim.features.warehouse.dto.UpdateCompartmentDTO;
import org.chainoptim.features.warehouse.model.Compartment;

import java.util.List;

public interface CompartmentService {

    List<Compartment> getCompartmentsByOrganizationId(Integer organizationId);
    List<Compartment> getCompartmentsByWarehouseId(Integer warehouseId);
    Compartment getCompartmentById(Integer compartmentId);
    Compartment createCompartment(CreateCompartmentDTO compartmentDTO);
    Compartment updateCompartment(UpdateCompartmentDTO compartmentDTO);
    void deleteCompartment(Integer compartmentId);
}

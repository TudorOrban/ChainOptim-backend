package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.dto.CompartmentDTOMapper;
import org.chainoptim.features.warehouse.dto.CreateCompartmentDTO;
import org.chainoptim.features.warehouse.dto.UpdateCompartmentDTO;
import org.chainoptim.features.warehouse.model.Compartment;
import org.chainoptim.features.warehouse.repository.CompartmentRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CompartmentServiceImpl implements CompartmentService {

    private final CompartmentRepository compartmentRepository;

    @Autowired
    public CompartmentServiceImpl(CompartmentRepository compartmentRepository) {
        this.compartmentRepository = compartmentRepository;
    }

    public List<Compartment> getCompartmentsByOrganizationId(Integer organizationId) {
        return compartmentRepository.findByOrganizationId(organizationId);
    }

    public List<Compartment> getCompartmentsByWarehouseId(Integer warehouseId) {
        return compartmentRepository.findByWarehouseId(warehouseId);
    }

    public Compartment getCompartmentById(Integer compartmentId) {
        return compartmentRepository.findById(compartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Compartment with ID: " + compartmentId + " not found."));
    }

    public Compartment createCompartment(CreateCompartmentDTO compartmentDTO) {
        Compartment compartment = CompartmentDTOMapper.mapCreateCompartmentDTOToCompartment(compartmentDTO);
        return compartmentRepository.save(compartment);
    }

    public Compartment updateCompartment(UpdateCompartmentDTO compartmentDTO) {
        Compartment compartment = compartmentRepository.findById(compartmentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compartment with ID: " + compartmentDTO.getId() + " not found."));

        Compartment updatedCompartment = CompartmentDTOMapper.setUpdateCompartmentDTOToCompartment(compartmentDTO, compartment);
        return compartmentRepository.save(updatedCompartment);
    }

    public void deleteCompartment(Integer compartmentId) {
        compartmentRepository.deleteById(compartmentId);
    }
}

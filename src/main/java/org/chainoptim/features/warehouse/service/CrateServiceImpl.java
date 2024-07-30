package org.chainoptim.features.warehouse.service;

import org.chainoptim.features.warehouse.dto.CrateDTOMapper;
import org.chainoptim.features.warehouse.dto.CreateCrateDTO;
import org.chainoptim.features.warehouse.dto.UpdateCrateDTO;
import org.chainoptim.features.warehouse.model.Crate;
import org.chainoptim.features.warehouse.repository.CrateRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrateServiceImpl implements CrateService {

    private final CrateRepository crateRepository;

    @Autowired
    public CrateServiceImpl(CrateRepository crateRepository) {
        this.crateRepository = crateRepository;
    }

    public List<Crate> getCratesByOrganizationId(Integer organizationId) {
        return crateRepository.findByOrganizationId(organizationId);
    }

    public Crate createCrate(CreateCrateDTO crateDTO) {
        Crate crate = CrateDTOMapper.mapCreateCrateDTOToCrate(crateDTO);
        return crateRepository.save(crate);
    }

    public Crate updateCrate(UpdateCrateDTO crateDTO) {
        Crate crate = crateRepository.findById(crateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Crate with ID: " + crateDTO.getId() + " not found."));
        CrateDTOMapper.setUpdateCrateDTOToCrate(crateDTO, crate);
        return crateRepository.save(crate);
    }

    public void deleteCrate(Integer crateId) {
        crateRepository.deleteById(crateId);
    }
}

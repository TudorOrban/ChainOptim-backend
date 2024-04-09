package org.chainoptim.core.scsnapshot.service;

import org.chainoptim.core.scsnapshot.dto.CreateSnapshotDTO;
import org.chainoptim.core.scsnapshot.dto.SnapshotDTOMapper;
import org.chainoptim.core.scsnapshot.dto.UpdateSnapshotDTO;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.repository.SupplyChainSnapshotRepository;
import org.chainoptim.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SnapshotPersistenceServiceImpl implements SnapshotPersistenceService {

    private final SupplyChainSnapshotRepository snapshotRepository;

    @Autowired
    public SnapshotPersistenceServiceImpl(SupplyChainSnapshotRepository snapshotRepository) {
        this.snapshotRepository = snapshotRepository;
    }

    public SupplyChainSnapshot getSupplyChainSnapshotByOrganizationId(Integer organizationId) {
        return snapshotRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Snapshot with organization ID: " + organizationId + " not found"));
    }

    public Optional<Integer> getIdByOrganizationId(Integer organizationId) {
        return snapshotRepository.findIdByOrganizationId(organizationId);
    }

    public SupplyChainSnapshot createSnapshot(CreateSnapshotDTO snapshotDTO) {
        return snapshotRepository.save(SnapshotDTOMapper.mapCreateSnapshotDTOToSnapshot(snapshotDTO));
    }

    public SupplyChainSnapshot updateSnapshot(UpdateSnapshotDTO snapshotDTO) {
        SupplyChainSnapshot snapshot = snapshotRepository.findById(snapshotDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Snapshot with ID: " + snapshotDTO.getId() + " not found"));
        SnapshotDTOMapper.setUpdateSnapshotDTOToSnapshot(snapshotDTO, snapshot);
        return snapshotRepository.save(snapshot);
    }

    public void deleteSnapshot(Integer snapshotId) {
        snapshotRepository.deleteById(snapshotId);
    }
}

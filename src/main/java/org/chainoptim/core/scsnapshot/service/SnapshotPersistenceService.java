package org.chainoptim.core.scsnapshot.service;

import org.chainoptim.core.scsnapshot.dto.CreateSnapshotDTO;
import org.chainoptim.core.scsnapshot.dto.UpdateSnapshotDTO;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;

import java.util.Optional;

public interface SnapshotPersistenceService {

    SupplyChainSnapshot getSupplyChainSnapshotByOrganizationId(Integer organizationId);
    Optional<Integer> getIdByOrganizationId(Integer organizationId);
    SupplyChainSnapshot createSnapshot(CreateSnapshotDTO snapshotDTO);
    SupplyChainSnapshot updateSnapshot(UpdateSnapshotDTO snapshotDTO);
    void deleteSnapshot(Integer snapshotId);
}

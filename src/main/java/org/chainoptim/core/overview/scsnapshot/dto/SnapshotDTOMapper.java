package org.chainoptim.core.overview.scsnapshot.dto;

import org.chainoptim.core.overview.scsnapshot.model.SupplyChainSnapshot;

public class SnapshotDTOMapper {

    private SnapshotDTOMapper() {}

    public static SupplyChainSnapshot mapCreateSnapshotDTOToSnapshot(CreateSnapshotDTO createSnapshotDTO) {
        SupplyChainSnapshot snapshot = new SupplyChainSnapshot();
        snapshot.setOrganizationId(createSnapshotDTO.getOrganizationId());
        snapshot.setSnapshot(createSnapshotDTO.getSnapshot());
        return snapshot;
    }

    public static void setUpdateSnapshotDTOToSnapshot(UpdateSnapshotDTO updateSnapshotDTO, SupplyChainSnapshot snapshot) {
        snapshot.setOrganizationId(updateSnapshotDTO.getOrganizationId());
        snapshot.setSnapshot(updateSnapshotDTO.getSnapshot());
    }
}

package org.chainoptim.core.overview.scsnapshot.dto;

import org.chainoptim.core.overview.scsnapshot.model.Snapshot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSnapshotDTO {

    private Integer id;
    private Integer organizationId;
    private Snapshot snapshot;
}

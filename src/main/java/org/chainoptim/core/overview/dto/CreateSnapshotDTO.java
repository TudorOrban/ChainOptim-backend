package org.chainoptim.core.overview.dto;

import org.chainoptim.core.overview.model.Snapshot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSnapshotDTO {

    private Integer organizationId;
    private Snapshot snapshot;
}

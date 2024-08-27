package org.chainoptim.core.overview.scsnapshot.service;

import org.chainoptim.core.overview.scsnapshot.model.Snapshot;

public interface SnapshotFinderService {

    Snapshot getSnapshotByOrganizationId(Integer organizationId);
}

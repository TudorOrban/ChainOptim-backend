package org.chainoptim.core.scsnapshot.service;

import org.chainoptim.core.scsnapshot.model.Snapshot;

public interface SnapshotFinderService {

    Snapshot getSnapshotByOrganizationId(Integer organizationId);
}

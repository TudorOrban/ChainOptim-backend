package org.chainoptim.core.overview.service;

import org.chainoptim.core.overview.model.Snapshot;

public interface SnapshotFinderService {

    Snapshot getSnapshotByOrganizationId(Integer organizationId);
}

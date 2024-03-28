package org.chainoptim.core.overview.service;

import org.chainoptim.core.overview.model.SupplyChainSnapshot;

public interface SupplyChainSnapshotService {

    SupplyChainSnapshot getSupplyChainSnapshot(Integer organizationId);
}

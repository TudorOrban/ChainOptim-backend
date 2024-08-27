package org.chainoptim.core.overview.map.service;

import org.chainoptim.core.overview.map.model.MapData;

public interface SupplyChainMapRefreshService {

    MapData refreshSupplyChainMap(Integer organizationId);
}

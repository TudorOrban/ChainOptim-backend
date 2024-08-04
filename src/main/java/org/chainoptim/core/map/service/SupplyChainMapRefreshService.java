package org.chainoptim.core.map.service;

import org.chainoptim.core.map.model.MapData;

public interface SupplyChainMapRefreshService {

    MapData refreshSupplyChainMap(Integer organizationId);
}

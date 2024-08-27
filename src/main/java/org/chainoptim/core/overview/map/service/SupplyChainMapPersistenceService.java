package org.chainoptim.core.overview.map.service;

import org.chainoptim.core.overview.map.model.SupplyChainMap;

public interface SupplyChainMapPersistenceService {

    SupplyChainMap getMapByOrganizationId(Integer organizationId);
    SupplyChainMap refreshMap(Integer organizationId);
}

package org.chainoptim.core.map.service;

import org.chainoptim.core.map.model.SupplyChainMap;

public interface SupplyChainMapPersistenceService {

    SupplyChainMap getMapByOrganizationId(Integer organizationId);
    SupplyChainMap refreshMap(Integer organizationId);
}

package org.chainoptim.core.overview.map.service;

import org.chainoptim.core.overview.map.model.MapData;
import org.chainoptim.core.overview.map.model.SupplyChainMap;
import org.chainoptim.core.overview.map.repository.SupplyChainMapRepository;
import org.chainoptim.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SupplyChainMapPersistenceServiceImpl implements SupplyChainMapPersistenceService {

    private final SupplyChainMapRepository supplyChainMapRepository;
    private final SupplyChainMapRefreshService supplyChainMapRefreshService;

    private static final int REFRESH_COOLDOWN = 5000; // 24 hours

    @Autowired
    public SupplyChainMapPersistenceServiceImpl(SupplyChainMapRepository supplyChainMapRepository,
                                                SupplyChainMapRefreshService supplyChainMapRefreshService) {
        this.supplyChainMapRepository = supplyChainMapRepository;
        this.supplyChainMapRefreshService = supplyChainMapRefreshService;
    }

    public SupplyChainMap getMapByOrganizationId(Integer organizationId) {
        return supplyChainMapRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Supply chain map with organization ID: " + organizationId + " not found"));
    }

    public SupplyChainMap refreshMap(Integer organizationId) {
        SupplyChainMap map = supplyChainMapRepository.findByOrganizationId(organizationId).orElse(null);
        if (map != null && map.getUpdatedAt() != null && map.getUpdatedAt().plusSeconds(REFRESH_COOLDOWN).isAfter(LocalDateTime.now())) {
            return map;
        }

        if (map == null) {
            map = new SupplyChainMap();
            map.setOrganizationId(organizationId);
        }

        MapData mapData = supplyChainMapRefreshService.refreshSupplyChainMap(organizationId);
        map.setMapData(mapData);

        return supplyChainMapRepository.save(map);
    }
}

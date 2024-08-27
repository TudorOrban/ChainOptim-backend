package org.chainoptim.core.overview.map.controller;

import org.chainoptim.core.general.security.service.SecurityService;
import org.chainoptim.core.overview.map.model.SupplyChainMap;
import org.chainoptim.core.overview.map.service.SupplyChainMapPersistenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/supply-chain-maps")
public class SupplyChainMapController {

    private final SupplyChainMapPersistenceService supplyChainMapPersistenceService;
    private final SecurityService securityService;

    @Autowired
    public SupplyChainMapController(SupplyChainMapPersistenceService supplyChainMapPersistenceService,
                                     SecurityService securityService) {
        this.supplyChainMapPersistenceService = supplyChainMapPersistenceService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Organization\", \"Read\")")
    @GetMapping("/{organizationId}")
    public ResponseEntity<SupplyChainMap> getSupplyChainMapByOrganizationId(@PathVariable Integer organizationId) {
        SupplyChainMap supplyChainMap = supplyChainMapPersistenceService.getMapByOrganizationId(organizationId);
        return ResponseEntity.ok(supplyChainMap);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Organization\", \"Update\")")
    @GetMapping("/{organizationId}/refresh")
    public ResponseEntity<SupplyChainMap> refreshSupplyChainMap(@PathVariable Integer organizationId) {
        SupplyChainMap supplyChainMap = supplyChainMapPersistenceService.refreshMap(organizationId);
        return ResponseEntity.ok(supplyChainMap);
    }
}

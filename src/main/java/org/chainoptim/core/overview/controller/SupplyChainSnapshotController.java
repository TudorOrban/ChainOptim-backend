package org.chainoptim.core.overview.controller;

import org.chainoptim.core.overview.model.SupplyChainSnapshot;
import org.chainoptim.core.overview.service.SupplyChainSnapshotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/supply-chain-snapshots")
public class SupplyChainSnapshotController {

    private final SupplyChainSnapshotService supplyChainSnapshotService;

    @Autowired
    public SupplyChainSnapshotController(SupplyChainSnapshotService supplyChainSnapshotService) {
        this.supplyChainSnapshotService = supplyChainSnapshotService;
    }

    @GetMapping("/organization/{organizationId}")
    public SupplyChainSnapshot getSupplyChainSnapshot(@PathVariable("organizationId") Integer organizationId) {
        return supplyChainSnapshotService.getSupplyChainSnapshot(organizationId);
    }
}

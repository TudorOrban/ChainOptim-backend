package org.chainoptim.core.scsnapshot.controller;

import org.chainoptim.core.scsnapshot.dto.CreateSnapshotDTO;
import org.chainoptim.core.scsnapshot.dto.UpdateSnapshotDTO;
import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.service.SnapshotFinderService;
import org.chainoptim.core.scsnapshot.service.SnapshotPersistenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supply-chain-snapshots")
public class SupplyChainSnapshotController { // TODO: Secure these endpoints

    private final SnapshotFinderService snapshotFinderService;
    private final SnapshotPersistenceService snapshotPersistenceService;

    @Autowired
    public SupplyChainSnapshotController(SnapshotFinderService snapshotFinderService,
                                         SnapshotPersistenceService snapshotPersistenceService) {
        this.snapshotFinderService = snapshotFinderService;
        this.snapshotPersistenceService = snapshotPersistenceService;
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<SupplyChainSnapshot> getSupplyChainSnapshotFromStorage(@PathVariable("organizationId") Integer organizationId) {
        SupplyChainSnapshot snapshot = snapshotPersistenceService.getSupplyChainSnapshotByOrganizationId(organizationId);
        return ResponseEntity.ok(snapshot);
    }

    @GetMapping("/organization/{organizationId}/fresh")
    public ResponseEntity<Snapshot> getSupplyChainSnapshot(@PathVariable("organizationId") Integer organizationId) {
        Snapshot snapshot = snapshotFinderService.getSnapshotByOrganizationId(organizationId);
        return ResponseEntity.ok(snapshot);
    }

    @PostMapping("/create")
    public ResponseEntity<SupplyChainSnapshot> createSupplyChainSnapshot(@RequestBody CreateSnapshotDTO snapshotDTO) {
        SupplyChainSnapshot createdSnapshot = snapshotPersistenceService.createSnapshot(snapshotDTO);
        return ResponseEntity.ok(createdSnapshot);
    }

    @PostMapping("/compute/organization/{organizationId}")
    public ResponseEntity<SupplyChainSnapshot> computeSupplyChainSnapshot(@PathVariable("organizationId") Integer organizationId) {
        Snapshot snapshot = snapshotFinderService.getSnapshotByOrganizationId(organizationId);
        CreateSnapshotDTO snapshotDTO = new CreateSnapshotDTO(organizationId, snapshot);
        SupplyChainSnapshot createdSnapshot = snapshotPersistenceService.createSnapshot(snapshotDTO);
        return ResponseEntity.ok(createdSnapshot);
    }

    @PutMapping("/update")
    public ResponseEntity<SupplyChainSnapshot> updateSupplyChainSnapshot(@RequestBody UpdateSnapshotDTO snapshotDTO) {
        SupplyChainSnapshot updatedSnapshot = snapshotPersistenceService.updateSnapshot(snapshotDTO);
        return ResponseEntity.ok(updatedSnapshot);
    }

    @DeleteMapping("/delete/{snapshotId}")
    public ResponseEntity<Void> deleteSupplyChainSnapshot(@PathVariable("snapshotId") Integer snapshotId) {
        snapshotPersistenceService.deleteSnapshot(snapshotId);
        return ResponseEntity.noContent().build();
    }
}

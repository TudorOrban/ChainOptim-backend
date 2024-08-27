package org.chainoptim.features.storage.crate.controller;

import org.chainoptim.features.storage.crate.dto.CreateCrateDTO;
import org.chainoptim.features.storage.crate.dto.UpdateCrateDTO;
import org.chainoptim.features.storage.crate.model.Crate;
import org.chainoptim.features.storage.crate.service.CrateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crates")
public class CrateController {

    private final CrateService crateService;

    @Autowired
    public CrateController(CrateService crateService) {
        this.crateService = crateService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Crate\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Crate>> getCratesByOrganizationId(@PathVariable Integer organizationId) {
        List<Crate> crates = crateService.getCratesByOrganizationId(organizationId);
        if (crates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(crates);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#crateDTO.getOrganizationId(), \"Warehouse\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Crate> createCrate(@RequestBody CreateCrateDTO crateDTO) {
        Crate crate = crateService.createCrate(crateDTO);
        return ResponseEntity.ok(crate);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#crateDTO.getId(), \"Crate\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Crate> updateCrate(@RequestBody UpdateCrateDTO crateDTO) {
        Crate crate = crateService.updateCrate(crateDTO);
        return ResponseEntity.ok(crate);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#crateId, \"Crate\", \"Delete\")")
    @DeleteMapping("/delete/{crateId}")
    public ResponseEntity<Void> deleteCrate(@PathVariable Integer crateId) {
        crateService.deleteCrate(crateId);
        return ResponseEntity.ok().build();
    }
}

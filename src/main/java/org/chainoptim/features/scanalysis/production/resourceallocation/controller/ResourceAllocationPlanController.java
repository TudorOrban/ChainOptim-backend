package org.chainoptim.features.scanalysis.production.resourceallocation.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.scanalysis.production.resourceallocation.dto.CreateAllocationPlanDTO;
import org.chainoptim.features.scanalysis.production.resourceallocation.dto.UpdateAllocationPlanDTO;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocationPlan;
import org.chainoptim.features.scanalysis.production.resourceallocation.service.ResourceAllocationPlanPersistenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/active-resource-allocation-plans")
public class ResourceAllocationPlanController {

    private final ResourceAllocationPlanPersistenceService resourceAllocationPlanPersistenceService;
    private final SecurityService securityService;

    @Autowired
    public ResourceAllocationPlanController(ResourceAllocationPlanPersistenceService resourceAllocationPlanPersistenceService,
                                            SecurityService securityService) {
        this.resourceAllocationPlanPersistenceService = resourceAllocationPlanPersistenceService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\", \"Read\")")
    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<ResourceAllocationPlan> getActiveResourceAllocationPlan(@PathVariable Integer factoryId) {
        ResourceAllocationPlan resourceAllocationPlan = resourceAllocationPlanPersistenceService.getResourceAllocationPlan(factoryId);
        return ResponseEntity.ok(resourceAllocationPlan);
    }

    // Create
    @PreAuthorize("@securityService.canAccessEntity(#planDTO.getFactoryId(), \"Factory\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<ResourceAllocationPlan> createActiveResourceAllocationPlan(@RequestBody CreateAllocationPlanDTO planDTO) {
        ResourceAllocationPlan createdPlan = resourceAllocationPlanPersistenceService.createResourceAllocationPlan(planDTO);
        return ResponseEntity.ok(createdPlan);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#planDTO.getFactoryId(), \"Factory\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<ResourceAllocationPlan> updateActiveResourceAllocationPlan(@RequestBody UpdateAllocationPlanDTO planDTO) {
        ResourceAllocationPlan updatedPlan = resourceAllocationPlanPersistenceService.updateResourceAllocationPlan(planDTO);
        return ResponseEntity.ok(updatedPlan);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#id, \"Factory\", \"Delete\")")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteActiveResourceAllocationPlan(@PathVariable Integer id) {
        resourceAllocationPlanPersistenceService.deleteResourceAllocationPlan(id);
        return ResponseEntity.ok().build();
    }
}

package org.chainoptim.features.scanalysis.production.performance.controller;

import org.chainoptim.features.scanalysis.production.performance.dto.CreateFactoryPerformanceDTO;
import org.chainoptim.features.scanalysis.production.performance.dto.UpdateFactoryPerformanceDTO;
import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformance;
import org.chainoptim.features.scanalysis.production.performance.service.FactoryPerformancePersistenceService;
import org.chainoptim.features.scanalysis.production.performance.service.FactoryPerformanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/factory-performance")
public class FactoryPerformanceController {

    private final FactoryPerformanceService factoryPerformanceService;
    private final FactoryPerformancePersistenceService factoryPerformancePersistenceService;

    @Autowired
    public FactoryPerformanceController(
            FactoryPerformanceService factoryPerformanceService,
            FactoryPerformancePersistenceService factoryPerformancePersistenceService
    ) {
        this.factoryPerformanceService = factoryPerformanceService;
        this.factoryPerformancePersistenceService = factoryPerformancePersistenceService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\", \"Read\")")
    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<FactoryPerformance> getFactoryPerformance(@PathVariable Integer factoryId) {
        FactoryPerformance factoryPerformance = factoryPerformancePersistenceService.getFactoryPerformance(factoryId);
        return ResponseEntity.ok(factoryPerformance);
    }

    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\", \"Read\")")
    @GetMapping("/factory/{factoryId}/refresh")
    public ResponseEntity<FactoryPerformance> evaluateFactoryPerformance(@PathVariable Integer factoryId) {
        FactoryPerformance factoryPerformance = factoryPerformancePersistenceService.refreshFactoryPerformance(factoryId);
        return ResponseEntity.ok(factoryPerformance);
    }

    // Create
    @PreAuthorize("@securityService.canAccessEntity(#performanceDTO.getFactoryId(), \"Factory\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<FactoryPerformance> createFactoryPerformance(@RequestBody CreateFactoryPerformanceDTO performanceDTO) {
        FactoryPerformance createdFactoryPerformance = factoryPerformancePersistenceService.createFactoryPerformance(performanceDTO);
        return ResponseEntity.ok(createdFactoryPerformance);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#performanceDTO.getFactoryId(), \"Factory\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<FactoryPerformance> updateFactoryPerformance(@RequestBody UpdateFactoryPerformanceDTO performanceDTO) {
        FactoryPerformance updatedFactoryPerformance = factoryPerformancePersistenceService.updateFactoryPerformance(performanceDTO);
        return ResponseEntity.ok(updatedFactoryPerformance);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#id, \"Factory\", \"Delete\")")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFactoryPerformance(@PathVariable Integer id) {
        factoryPerformancePersistenceService.deleteFactoryPerformance(id);
        return ResponseEntity.noContent().build();
    }

}

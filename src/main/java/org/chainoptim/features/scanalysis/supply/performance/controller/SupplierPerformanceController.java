package org.chainoptim.features.scanalysis.supply.performance.controller;

import org.chainoptim.features.scanalysis.supply.performance.dto.CreateSupplierPerformanceDTO;
import org.chainoptim.features.scanalysis.supply.performance.dto.UpdateSupplierPerformanceDTO;
import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformance;
import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformanceReport;
import org.chainoptim.features.scanalysis.supply.performance.service.SupplierPerformancePersistenceService;
import org.chainoptim.features.scanalysis.supply.performance.service.SupplierPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supplier-performance")
public class SupplierPerformanceController {

    private final SupplierPerformanceService supplierPerformanceService;
    private final SupplierPerformancePersistenceService supplierPerformancePersistenceService;

    @Autowired
    public SupplierPerformanceController(
            SupplierPerformanceService supplierPerformanceService,
            SupplierPerformancePersistenceService supplierPerformancePersistenceService
    ) {
        this.supplierPerformanceService = supplierPerformanceService;
        this.supplierPerformancePersistenceService = supplierPerformancePersistenceService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessEntity(#supplierId, \"Supplier\", \"Read\")")
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<SupplierPerformance> getSupplierPerformance(@PathVariable Integer supplierId) {
        SupplierPerformance supplierPerformance = supplierPerformancePersistenceService.getSupplierPerformance(supplierId);
        return ResponseEntity.ok(supplierPerformance);
    }

    @PreAuthorize("@securityService.canAccessEntity(#supplierId, \"Supplier\", \"Read\")")
    @GetMapping("/supplier/{supplierId}/refresh")
    public ResponseEntity<SupplierPerformance> evaluateSupplierPerformance(@PathVariable Integer supplierId) {
        SupplierPerformance supplierPerformance = supplierPerformancePersistenceService.refreshSupplierPerformance(supplierId);
        return ResponseEntity.ok(supplierPerformance);
    }

    // Create
    @PreAuthorize("@securityService.canAccessEntity(#performanceDTO.getSupplierId(), \"Supplier\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<SupplierPerformance> createSupplierPerformance(@RequestBody CreateSupplierPerformanceDTO performanceDTO) {
        SupplierPerformance createdSupplierPerformance = supplierPerformancePersistenceService.createSupplierPerformance(performanceDTO);
        return ResponseEntity.ok(createdSupplierPerformance);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#performanceDTO.getSupplierId(), \"Supplier\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<SupplierPerformance> updateSupplierPerformance(@RequestBody UpdateSupplierPerformanceDTO performanceDTO) {
        SupplierPerformance updatedSupplierPerformance = supplierPerformancePersistenceService.updateSupplierPerformance(performanceDTO);
        return ResponseEntity.ok(updatedSupplierPerformance);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#id, \"Supplier\", \"Delete\")")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupplierPerformance(@PathVariable Integer id) {
        supplierPerformancePersistenceService.deleteSupplierPerformance(id);
        return ResponseEntity.noContent().build();
    }

}

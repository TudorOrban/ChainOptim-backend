package org.chainoptim.features.supplier.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.supplier.dto.CreateSupplierDTO;
import org.chainoptim.features.supplier.dto.SuppliersSearchDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierDTO;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.service.SupplierService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final SecurityService securityService;

    @Autowired
    public SupplierController(
            SupplierService supplierService,
            SecurityService securityService

    ) {
        this.supplierService = supplierService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Supplier\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Supplier>> getSuppliersByOrganizationId(@PathVariable Integer organizationId) {
        List<Supplier> suppliers = supplierService.getSuppliersByOrganizationId(organizationId);
        if (suppliers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(suppliers);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Supplier\", \"Read\")")
    @GetMapping("/organizations/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<SuppliersSearchDTO>> getSuppliersByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<SuppliersSearchDTO> suppliers = supplierService.getSuppliersByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(suppliers);
    }

    @PreAuthorize("@securityService.canAccessEntity(#supplierId, \"Supplier\", \"Read\")")
    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Integer supplierId) {
        Supplier supplier = supplierService.getSupplierById(supplierId);
        return ResponseEntity.ok(supplier);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#supplierDTO.getOrganizationId(), \"Supplier\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Supplier> createSupplier(@RequestBody CreateSupplierDTO supplierDTO) {
        Supplier supplier = supplierService.createSupplier(supplierDTO);
        return ResponseEntity.ok(supplier);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#supplierDTO.getId(), \"Supplier\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Supplier> updateSupplier(@RequestBody UpdateSupplierDTO supplierDTO) {
        Supplier supplier = supplierService.updateSupplier(supplierDTO);
        return ResponseEntity.ok(supplier);
    }

    @PreAuthorize("@securityService.canAccessEntity(#supplierId, \"Supplier\", \"Delete\")")
    @DeleteMapping("/delete/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok().build();
    }
}
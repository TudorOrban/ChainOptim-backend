package org.chainoptim.features.supply.controller;

import org.chainoptim.features.supply.dto.SuppliersSearchDTO;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Integer id) {
        return supplierService.getSupplierById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Supplier>> getSuppliersByOrganizationId(@PathVariable Integer organizationId) {
        List<Supplier> suppliers = supplierService.getSuppliersByOrganizationId(organizationId);
        if (suppliers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/organizations/advanced/{organizationId}")
    public ResponseEntity<?> getSuppliersByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending) {
        List<SuppliersSearchDTO> suppliers = supplierService.getSuppliersByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending);
        return ResponseEntity.ok(suppliers);
    }
}
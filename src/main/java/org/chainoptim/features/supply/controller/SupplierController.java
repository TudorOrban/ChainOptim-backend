package org.chainoptim.features.supply.controller;

import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

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
}
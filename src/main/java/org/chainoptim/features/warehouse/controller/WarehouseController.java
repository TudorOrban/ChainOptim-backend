package org.chainoptim.features.warehouse.controller;

import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Integer id) {
        return warehouseService.getWarehouseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Warehouse>> getWarehousesByOrganizationId(@PathVariable Integer organizationId) {
        List<Warehouse> warehouses = warehouseService.getWarehousesByOrganizationId(organizationId);
        if (warehouses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(warehouses);
    }
}
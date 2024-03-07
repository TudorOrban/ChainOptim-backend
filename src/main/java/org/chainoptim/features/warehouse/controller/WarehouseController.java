package org.chainoptim.features.warehouse.controller;

import org.chainoptim.features.warehouse.dto.CreateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.WarehousesSearchDTO;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.service.WarehouseService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }


    // Fetch
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

    @GetMapping("/organizations/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<WarehousesSearchDTO>> getWarehousesByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<WarehousesSearchDTO> warehouses = warehouseService.getWarehousesByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(warehouses);
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody CreateWarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseService.createWarehouse(warehouseDTO);
        return ResponseEntity.ok(warehouse);
    }

    // Update
    @PutMapping("/update")
    public ResponseEntity<Warehouse> updateWarehouse(@RequestBody UpdateWarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseService.updateWarehouse(warehouseDTO);
        return ResponseEntity.ok(warehouse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Integer id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok().build();
    }
}
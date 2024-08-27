package org.chainoptim.features.storage.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.storage.dto.CreateWarehouseDTO;
import org.chainoptim.features.storage.dto.UpdateWarehouseDTO;
import org.chainoptim.features.storage.dto.WarehouseOverviewDTO;
import org.chainoptim.features.storage.dto.WarehousesSearchDTO;
import org.chainoptim.features.storage.model.Warehouse;
import org.chainoptim.features.storage.service.WarehouseService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final SecurityService securityService;

    @Autowired
    public WarehouseController(
            WarehouseService warehouseService,
            SecurityService securityService
    ) {
        this.warehouseService = warehouseService;
        this.securityService = securityService;
    }


    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Warehouse\", \"Read\")")
    @GetMapping("/organization/{organizationId}/small")
    public ResponseEntity<List<WarehousesSearchDTO>> getWarehousesByOrganizationIdSmall(@PathVariable Integer organizationId) {
        List<WarehousesSearchDTO> warehouses = warehouseService.getWarehousesByOrganizationIdSmall(organizationId);
        if (warehouses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(warehouses);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Warehouse\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Warehouse>> getWarehousesByOrganizationId(@PathVariable Integer organizationId) {
        List<Warehouse> warehouses = warehouseService.getWarehousesByOrganizationId(organizationId);
        if (warehouses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(warehouses);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Warehouse\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
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

    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\", \"Read\")")
    @GetMapping("/{warehouseId}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Integer warehouseId) {
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        return ResponseEntity.ok(warehouse);
    }

    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\", \"Read\")")
    @GetMapping("/{warehouseId}/overview")
    public ResponseEntity<WarehouseOverviewDTO> getWarehouseOverview(@PathVariable Integer warehouseId) {
        WarehouseOverviewDTO overviewDTO = warehouseService.getWarehouseOverview(warehouseId);
        return ResponseEntity.ok(overviewDTO);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#warehouseDTO.getOrganizationId(), \"Warehouse\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody CreateWarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseService.createWarehouse(warehouseDTO);
        return ResponseEntity.ok(warehouse);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#warehouseDTO.getId(), \"Warehouse\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Warehouse> updateWarehouse(@RequestBody UpdateWarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseService.updateWarehouse(warehouseDTO);
        return ResponseEntity.ok(warehouse);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\", \"Delete\")")
    @DeleteMapping("/delete/{warehouseId}")
    public ResponseEntity<Integer> deleteWarehouse(@PathVariable Integer warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
        return ResponseEntity.ok(warehouseId);
    }
}
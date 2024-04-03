package org.chainoptim.features.warehouse.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.warehouse.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.features.warehouse.service.WarehouseInventoryService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses/inventory")
public class WarehouseInventoryController {

    private final WarehouseInventoryService warehouseInventoryService;
    private final SecurityService securityService;

    @Autowired
    public WarehouseInventoryController(
            WarehouseInventoryService warehouseInventoryService,
            SecurityService securityService
    ) {
        this.warehouseInventoryService = warehouseInventoryService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\", \"Read\")")
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<WarehouseInventoryItem>> getWarehouseInventoryByWarehouseId(@PathVariable Integer warehouseId) {
        List<WarehouseInventoryItem> items = warehouseInventoryService.getWarehouseInventoryItemsByWarehouseId(warehouseId);
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\", \"Read\")")
    @GetMapping("/warehouse/advanced/{warehouseId}")
    public ResponseEntity<PaginatedResults<WarehouseInventoryItem>> getWarehouseInventoryItemsByWarehouseId(
            @PathVariable Integer warehouseId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage) {
        PaginatedResults<WarehouseInventoryItem> warehouseItems = warehouseInventoryService.getWarehouseInventoryItemsByWarehouseIdAdvanced(warehouseId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(warehouseItems);
    }

//    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\")")
    // TODO: Secure endpoint
    @GetMapping("/{itemId}")
    public ResponseEntity<WarehouseInventoryItem> getWarehouseInventoryItemById(@PathVariable Integer itemId) {
        WarehouseInventoryItem item = warehouseInventoryService.getWarehouseInventoryItemById(itemId);
        return ResponseEntity.ok(item);
    }

    // Create
    @PreAuthorize("@securityService.canAccessEntity(#itemDTO.getWarehouseId(), \"Warehouse\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<WarehouseInventoryItem> createWarehouseInventoryItem(@RequestBody CreateWarehouseInventoryItemDTO itemDTO) {
        WarehouseInventoryItem item = warehouseInventoryService.createWarehouseInventoryItem(itemDTO);
        return ResponseEntity.ok(item);
    }

    // TODO: Secure endpoint
    @PostMapping("/create/bulk")
    public ResponseEntity<List<WarehouseInventoryItem>> createWarehouseInventoryItemsInBulk(@RequestBody List<CreateWarehouseInventoryItemDTO> itemDTOs) {
        List<WarehouseInventoryItem> items = warehouseInventoryService.createWarehouseInventoryItemsInBulk(itemDTOs);
        return ResponseEntity.ok(items);
    }

    // Update
//    @PreAuthorize("@securityService.canAccessEntity(#warehouseDTO.getId(), \"Warehouse\")")
    // TODO: Secure endpoint
    @PutMapping("/update")
    public ResponseEntity<WarehouseInventoryItem> updateWarehouseInventoryItem(@RequestBody UpdateWarehouseInventoryItemDTO itemDTO) {
        WarehouseInventoryItem item = warehouseInventoryService.updateWarehouseInventoryItem(itemDTO);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/update/bulk")
    public ResponseEntity<List<WarehouseInventoryItem>> updateWarehouseInventoryItemsInBulk(@RequestBody List<UpdateWarehouseInventoryItemDTO> itemDTOs) {
        List<WarehouseInventoryItem> items = warehouseInventoryService.updateWarehouseInventoryItemsInBulk(itemDTOs);
        return ResponseEntity.ok(items);
    }

    // Delete
//    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\")")
    // TODO: Secure endpoint
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteWarehouseInventoryItem(@PathVariable Integer itemId) {
        warehouseInventoryService.deleteWarehouseInventoryItem(itemId);
        return ResponseEntity.ok().build();
    }
}
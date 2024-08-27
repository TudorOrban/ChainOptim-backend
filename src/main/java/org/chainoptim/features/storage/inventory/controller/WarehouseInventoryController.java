package org.chainoptim.features.storage.inventory.controller;

import org.chainoptim.core.general.security.service.SecurityService;
import org.chainoptim.features.storage.inventory.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.storage.inventory.dto.UpdateWarehouseInventoryItemDTO;
import org.chainoptim.features.storage.inventory.model.WarehouseInventoryItem;
import org.chainoptim.features.storage.inventory.service.WarehouseInventoryService;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse-inventory-items")
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
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Organization\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<WarehouseInventoryItem>> getWarehouseInventoryItemsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "filters", required = false, defaultValue = "") String filtersJson,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        SearchParams searchParams = new SearchParams(searchQuery, filtersJson, null, sortBy, ascending, page, itemsPerPage);
        PaginatedResults<WarehouseInventoryItem> inventoryItems = warehouseInventoryService.getWarehouseInventoryItemsAdvanced(SearchMode.ORGANIZATION, organizationId, searchParams);
        return ResponseEntity.ok(inventoryItems);
    }

    @PreAuthorize("@securityService.canAccessEntity(#warehouseId, \"Warehouse\", \"Read\")")
    @GetMapping("/warehouse/advanced/{warehouseId}")
    public ResponseEntity<PaginatedResults<WarehouseInventoryItem>> getWarehouseInventoryItemsByWarehouseIdAdvanced(
            @PathVariable Integer warehouseId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "filters", required = false, defaultValue = "{}") String filtersJson,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        SearchParams searchParams = new SearchParams(searchQuery, filtersJson, null, sortBy, ascending, page, itemsPerPage);
        PaginatedResults<WarehouseInventoryItem> warehouseInventoryItems = warehouseInventoryService.getWarehouseInventoryItemsAdvanced(SearchMode.SECONDARY, warehouseId, searchParams);
        return ResponseEntity.ok(warehouseInventoryItems);
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
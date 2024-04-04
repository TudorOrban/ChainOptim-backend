package org.chainoptim.features.factory.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.factory.dto.*;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.service.FactoryInventoryService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factories/inventory")
public class FactoryInventoryController {

    private final FactoryInventoryService factoryInventoryService;
    private final SecurityService securityService;

    @Autowired
    public FactoryInventoryController(
            FactoryInventoryService factoryInventoryService,
            SecurityService securityService
    ) {
        this.factoryInventoryService = factoryInventoryService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\", \"Read\")")
    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<List<FactoryInventoryItem>> getFactoryInventoryByFactoryId(@PathVariable Integer factoryId) {
        List<FactoryInventoryItem> items = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\", \"Read\")")
    @GetMapping("/factory/advanced/{factoryId}")
    public ResponseEntity<PaginatedResults<FactoryInventoryItem>> getFactoryInventoryItemsByFactoryId(
            @PathVariable Integer factoryId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage) {
        PaginatedResults<FactoryInventoryItem> factoryItems = factoryInventoryService.getFactoryInventoryItemsByFactoryIdAdvanced(factoryId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(factoryItems);
    }

//    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    // TODO: Secure endpoint
    @GetMapping("/{itemId}")
    public ResponseEntity<FactoryInventoryItem> getFactoryInventoryItemById(@PathVariable Integer itemId) {
        FactoryInventoryItem item = factoryInventoryService.getFactoryInventoryItemById(itemId);
        return ResponseEntity.ok(item);
    }

    // Create
    @PreAuthorize("@securityService.canAccessEntity(#itemDTO.getFactoryId(), \"Factory\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<FactoryInventoryItem> createFactoryInventoryItem(@RequestBody CreateFactoryInventoryItemDTO itemDTO) {
        FactoryInventoryItem item = factoryInventoryService.createFactoryInventoryItem(itemDTO);
        return ResponseEntity.ok(item);
    }

    // TODO: Secure endpoint
    @PostMapping("/create/bulk")
    public ResponseEntity<List<FactoryInventoryItem>> createFactoryInventoryItemsInBulk(@RequestBody List<CreateFactoryInventoryItemDTO> itemDTOs) {
        List<FactoryInventoryItem> items = factoryInventoryService.createFactoryInventoryItemsInBulk(itemDTOs);
        return ResponseEntity.ok(items);
    }

    // Update
//    @PreAuthorize("@securityService.canAccessEntity(#factoryDTO.getId(), \"Factory\")")
    // TODO: Secure endpoint
    @PutMapping("/update")
    public ResponseEntity<FactoryInventoryItem> updateFactoryInventoryItem(@RequestBody UpdateFactoryInventoryItemDTO itemDTO) {
        FactoryInventoryItem item = factoryInventoryService.updateFactoryInventoryItem(itemDTO);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/update/bulk")
    public ResponseEntity<List<FactoryInventoryItem>> updateFactoryInventoryItemsInBulk(@RequestBody List<UpdateFactoryInventoryItemDTO> itemDTOs) {
        List<FactoryInventoryItem> items = factoryInventoryService.updateFactoryInventoryItemsInBulk(itemDTOs);
        return ResponseEntity.ok(items);
    }

    // Delete
//    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    // TODO: Secure endpoint
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteFactoryInventoryItem(@PathVariable Integer itemId) {
        factoryInventoryService.deleteFactoryInventoryItem(itemId);
        return ResponseEntity.ok().build();
    }
}
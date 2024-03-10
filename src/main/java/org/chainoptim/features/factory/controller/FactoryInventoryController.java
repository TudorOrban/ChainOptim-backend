package org.chainoptim.features.factory.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.factory.dto.*;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.service.FactoryInventoryService;
import org.chainoptim.features.factory.service.FactoryService;
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
    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<List<FactoryInventoryItem>> getFactoryInventoryByFactoryId(@PathVariable Integer factoryId) {
        List<FactoryInventoryItem> items = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    @GetMapping("/factory/advanced/{factoryId}")
    public ResponseEntity<PaginatedResults<FactoryInventoryItem>> getFactoryInventoryItemsByFactoryId(
            @PathVariable Integer factoryId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage) {
        PaginatedResults<FactoryInventoryItem> factoryItems = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(factoryItems);
    }

//    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    // TODO: Secure endpoint
    @GetMapping("/{itemId}")
    public ResponseEntity<FactoryInventoryItem> getFactoryInventoryItemById(@PathVariable Integer itemId) {
        return factoryInventoryService.getFactoryInventoryItemById(itemId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create
    @PreAuthorize("@securityService.canAccessEntity(#itemDTO.getFactoryId(), \"Factory\")")
    @PostMapping("/create")
    public ResponseEntity<FactoryInventoryItem> createFactory(@RequestBody CreateFactoryInventoryItemDTO itemDTO) {
        FactoryInventoryItem item = factoryInventoryService.createFactoryInventoryItem(itemDTO);
        return ResponseEntity.ok(item);
    }

    // Update
//    @PreAuthorize("@securityService.canAccessEntity(#factoryDTO.getId(), \"Factory\")")
    // TODO: Secure endpoint
    @PutMapping("/update")
    public ResponseEntity<FactoryInventoryItem> updateFactoryInventoryItem(@RequestBody UpdateFactoryInventoryItemDTO itemDTO) {
        FactoryInventoryItem item = factoryInventoryService.updateFactoryInventoryItem(itemDTO);
        return ResponseEntity.ok(item);
    }

    // Delete
//    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    // TODO: Secure endpoint
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteFactory(@PathVariable Long itemId) {
        factoryInventoryService.deleteFactoryInventoryItem(itemId);
        return ResponseEntity.ok().build();
    }
}
package org.chainoptim.features.factory.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.factory.dto.CreateFactoryInventoryItemDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryInventoryItemDTO;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.service.FactoryInventoryService;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factory-inventory-items")
public class FactoryInventoryController {

    private final FactoryInventoryService factoryInventoryService;
    private final SecurityService securityService;

    @Autowired
    public FactoryInventoryController(FactoryInventoryService factoryInventoryService,
                                      SecurityService securityService) {
        this.factoryInventoryService = factoryInventoryService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Organization\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<FactoryInventoryItem>> getFactoryInventoryItemsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "filters", required = false, defaultValue = "") String filtersJson,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        SearchParams searchParams = new SearchParams(searchQuery, filtersJson, null, sortBy, ascending, page, itemsPerPage);
        PaginatedResults<FactoryInventoryItem> inventoryItems = factoryInventoryService.getFactoryInventoryItemsAdvanced(SearchMode.ORGANIZATION, organizationId, searchParams);
        return ResponseEntity.ok(inventoryItems);
    }

    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\", \"Read\")")
    @GetMapping("/factory/advanced/{factoryId}")
    public ResponseEntity<PaginatedResults<FactoryInventoryItem>> getFactoryInventoryItemsByFactoryIdAdvanced(
            @PathVariable Integer factoryId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "filters", required = false, defaultValue = "{}") String filtersJson,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        SearchParams searchParams = new SearchParams(searchQuery, filtersJson, null, sortBy, ascending, page, itemsPerPage);
        PaginatedResults<FactoryInventoryItem> factoryInventoryItems = factoryInventoryService.getFactoryInventoryItemsAdvanced(SearchMode.SECONDARY, factoryId, searchParams);
        return ResponseEntity.ok(factoryInventoryItems);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTO.getOrganizationId(), \"FactoryInventoryItem\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<FactoryInventoryItem> createFactoryInventoryItem(@RequestBody CreateFactoryInventoryItemDTO orderDTO) {
        FactoryInventoryItem factoryInventoryItem = factoryInventoryService.createFactoryInventoryItem(orderDTO);
        return ResponseEntity.ok(factoryInventoryItem);
    }

//    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTOs.getFirst().getOrganizationId(), \"FactoryInventoryItem\", \"Create\")")
    @PostMapping("/create/bulk")
    public ResponseEntity<List<FactoryInventoryItem>> createFactoryInventoryItemsInBulk(@RequestBody List<CreateFactoryInventoryItemDTO> orderDTOs) {
        List<FactoryInventoryItem> factoryInventoryItems = factoryInventoryService.createFactoryInventoryItemsInBulk(orderDTOs);
        return ResponseEntity.ok(factoryInventoryItems);
    }

    // Update
//    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTOs.getFirst().getOrganizationId(), \"FactoryInventoryItem\", \"Update\")")
    @PutMapping("/update/bulk")
    public ResponseEntity<List<FactoryInventoryItem>> updateFactoryInventoryItemsInBulk(@RequestBody List<UpdateFactoryInventoryItemDTO> orderDTOs) {
        List<FactoryInventoryItem> factoryInventoryItems = factoryInventoryService.updateFactoryInventoryItemsInBulk(orderDTOs);
        return ResponseEntity.ok(factoryInventoryItems);
    }

    // Delete
//    @PreAuthorize("@securityService.canAccessEntity(#orderIds.getFirst(), \"FactoryInventoryItem\", \"Delete\")") // Secure as service method ensures all orders belong to the same organization
    @DeleteMapping("/delete/bulk")
    public ResponseEntity<List<Integer>> deleteFactoryInventoryItemsInBulk(@RequestBody List<Integer> orderIds) {
        factoryInventoryService.deleteFactoryInventoryItemsInBulk(orderIds);

        return ResponseEntity.ok().build();
    }
}

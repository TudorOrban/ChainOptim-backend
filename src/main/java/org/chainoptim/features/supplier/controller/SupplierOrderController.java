package org.chainoptim.features.supplier.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierOrderDTO;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.service.SupplierOrderService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/supplier-orders")
public class SupplierOrderController {

    private final SupplierOrderService supplierOrderService;
    private final SecurityService securityService;

    @Autowired
    public SupplierOrderController(SupplierOrderService supplierOrderService,
                                   SecurityService securityService) {
        this.supplierOrderService = supplierOrderService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Supplier\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<SupplierOrder>> getSupplierOrdersByOrganizationId(@PathVariable Integer organizationId) {
        List<SupplierOrder> supplierOrders = supplierOrderService.getSupplierOrdersByOrganizationId(organizationId);
        return ResponseEntity.ok(supplierOrders);
    }

    @PreAuthorize("@securityService.canAccessEntity(#supplierId, \"Supplier\", \"Read\")")
    @GetMapping("/organization/advanced/{supplierId}")
    public ResponseEntity<PaginatedResults<SupplierOrder>> getSupplierOrdersBySupplierIdAdvanced(
            @PathVariable Integer supplierId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "filters", required = false, defaultValue = "") String filtersJson,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<SupplierOrder> supplierOrders = supplierOrderService.getSupplierOrdersBySupplierIdAdvanced(supplierId, searchQuery, filtersJson, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(supplierOrders);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTO.getOrganizationId(), \"SupplierOrder\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<SupplierOrder> createSupplierOrder(@RequestBody CreateSupplierOrderDTO orderDTO) {
        SupplierOrder supplierOrder = supplierOrderService.createSupplierOrder(orderDTO);
        return ResponseEntity.ok(supplierOrder);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTOs.getFirst().getOrganizationId(), \"SupplierOrder\", \"Create\")")
    @PostMapping("/create/bulk")
    public ResponseEntity<List<SupplierOrder>> createSupplierOrdersInBulk(@RequestBody List<CreateSupplierOrderDTO> orderDTOs) {
        List<SupplierOrder> supplierOrders = supplierOrderService.createSupplierOrdersInBulk(orderDTOs);
        return ResponseEntity.ok(supplierOrders);
    }

    // Update
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTOs.getFirst().getOrganizationId(), \"SupplierOrder\", \"Update\")")
    @PutMapping("/update/bulk")
    public ResponseEntity<List<SupplierOrder>> updateSupplierOrdersInBulk(@RequestBody List<UpdateSupplierOrderDTO> orderDTOs) {
        List<SupplierOrder> supplierOrders = supplierOrderService.updateSuppliersOrdersInBulk(orderDTOs);
        return ResponseEntity.ok(supplierOrders);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#orderIds.getFirst(), \"SupplierOrder\", \"Delete\")") // Secure as service method ensures all orders belong to the same organization
    @DeleteMapping("/delete/bulk")
    public ResponseEntity<List<Integer>> deleteSupplierOrdersInBulk(@RequestBody List<Integer> orderIds) {
        supplierOrderService.deleteSupplierOrdersInBulk(orderIds);

        return ResponseEntity.ok(orderIds);
    }

}
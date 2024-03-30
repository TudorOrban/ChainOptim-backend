package org.chainoptim.features.supplier.controller;

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

@RestController
@RequestMapping("/api/v1/supplier-orders")
public class SupplierOrderController {

    private final SupplierOrderService supplierOrderService;

    @Autowired
    public SupplierOrderController(SupplierOrderService supplierOrderService) {
        this.supplierOrderService = supplierOrderService;
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<SupplierOrder>> getSuppliersByOrganizationId(@PathVariable Integer organizationId) {
        List<SupplierOrder> supplierOrders = supplierOrderService.getSupplierOrdersByOrganizationId(organizationId);
        return ResponseEntity.ok(supplierOrders);
    }

//    @PreAuthorize("@securityService.canAccessEntity(#supplierId, \"Supplier\", \"Read\")")
    @GetMapping("/organization/advanced/{supplierId}")
    public ResponseEntity<PaginatedResults<SupplierOrder>> getSupplierOrdersBySupplierIdAdvanced(
            @PathVariable Integer supplierId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<SupplierOrder> supplierOrders = supplierOrderService.getSuppliersBySupplierIdAdvanced(supplierId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(supplierOrders);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#order.getOrganizationId(), \"Supplier\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<SupplierOrder> createSupplierOrder(@RequestBody CreateSupplierOrderDTO order) {
        SupplierOrder supplierOrder = supplierOrderService.createSupplierOrder(order);
        return ResponseEntity.ok(supplierOrder);
    }

    // TODO: Secure endpoint
    @PostMapping("/create/bulk")
    public ResponseEntity<List<SupplierOrder>> createSupplierOrdersInBulk(@RequestBody List<CreateSupplierOrderDTO> orders) {
        List<SupplierOrder> clientOrders = supplierOrderService.createSupplierOrdersInBulk(orders);
        return ResponseEntity.ok(clientOrders);
    }

    // TODO: Secure endpoint
    @PutMapping("/update/bulk")
    public ResponseEntity<List<SupplierOrder>> updateSupplierOrdersInBulk(@RequestBody List<UpdateSupplierOrderDTO> orders) {
        List<SupplierOrder> clientOrders = supplierOrderService.updateSuppliersOrdersInBulk(orders);
        return ResponseEntity.ok(clientOrders);
    }
}

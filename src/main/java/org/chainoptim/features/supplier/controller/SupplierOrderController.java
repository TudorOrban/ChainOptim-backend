package org.chainoptim.features.supplier.controller;

import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.service.SupplierOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create")
    public ResponseEntity<SupplierOrder> createSupplierOrder(@RequestBody CreateSupplierOrderDTO order) {
        SupplierOrder supplierOrder = supplierOrderService.saveOrUpdateSupplierOrder(order);
        return ResponseEntity.ok(supplierOrder);
    }
}

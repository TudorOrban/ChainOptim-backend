package org.chainoptim.features.supply.controller;

import org.chainoptim.features.supply.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supply.model.SupplierOrder;
import org.chainoptim.features.supply.service.SupplierOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-orders")
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

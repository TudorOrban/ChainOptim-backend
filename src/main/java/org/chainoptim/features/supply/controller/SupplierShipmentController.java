package org.chainoptim.features.supply.controller;

import org.chainoptim.features.supply.model.SupplierShipment;
import org.chainoptim.features.supply.service.SupplierShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers/shipments")
public class SupplierShipmentController {

    private final SupplierShipmentService supplierShipmentService;

    @Autowired
    public SupplierShipmentController(SupplierShipmentService supplierShipmentService) {
        this.supplierShipmentService = supplierShipmentService;
    }

    @RequestMapping("/order/{orderId}")
    public ResponseEntity<List<SupplierShipment>> getShipmentsByOrderId(@PathVariable("orderId") Integer orderId) {
        List<SupplierShipment> shipments = supplierShipmentService.getSupplierShipmentBySupplyOrderId(orderId);
        return ResponseEntity.ok(shipments);
    }
}

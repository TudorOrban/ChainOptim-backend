package org.chainoptim.features.supplier.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.supplier.dto.CreateSupplierShipmentDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierShipmentDTO;
import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.features.supplier.service.SupplierShipmentService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier-shipments")
public class SupplierShipmentController {

    private final SupplierShipmentService supplierShipmentService;
    private final SecurityService securityService;

    @Autowired
    public SupplierShipmentController(SupplierShipmentService supplierShipmentService,
                                      SecurityService securityService) {
        this.supplierShipmentService = supplierShipmentService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canAccessEntity(#supplierOrderId, \"SupplierOrder\", \"Read\")")
    @GetMapping("/order/{supplierOrderId}")
    public ResponseEntity<List<SupplierShipment>> getSupplierShipmentsBySupplierOrderId(@PathVariable("supplierOrderId") Integer supplierOrderId) {
        List<SupplierShipment> supplierShipments = supplierShipmentService.getSupplierShipmentBySupplierOrderId(supplierOrderId);
        return ResponseEntity.ok(supplierShipments);
    }

    @PreAuthorize("@securityService.canAccessEntity(#supplierOrderId, \"SupplierOrder\", \"Read\")")
    @GetMapping("/order/advanced/{supplierOrderId}")
    public ResponseEntity<PaginatedResults<SupplierShipment>> getSupplierShipmentsBySupplierOrderIdAdvanced(
            @PathVariable Integer supplierOrderId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<SupplierShipment> supplierShipments = supplierShipmentService.getSupplierShipmentsBySupplierOrderIdAdvanced(supplierOrderId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(supplierShipments);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#shipmentDTO.getOrganizationId(), \"Supplier\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<SupplierShipment> createSupplierShipment(@RequestBody CreateSupplierShipmentDTO shipmentDTO) {
        SupplierShipment supplierShipment = supplierShipmentService.createSupplierShipment(shipmentDTO);
        return ResponseEntity.ok(supplierShipment);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#shipmentDTOs.getFirst().getOrganizationId(), \"Supplier\", \"Create\")")
    @PostMapping("/create/bulk")
    public ResponseEntity<List<SupplierShipment>> createSupplierShipmentsInBulk(@RequestBody List<CreateSupplierShipmentDTO> shipmentDTOs) {
        List<SupplierShipment> supplierShipments = supplierShipmentService.createSupplierShipmentsInBulk(shipmentDTOs);
        return ResponseEntity.ok(supplierShipments);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#shipmentDTOs.getFirst().getOrganizationId(), \"Supplier\", \"Update\")")
    @PutMapping("/update/bulk")
    public ResponseEntity<List<SupplierShipment>> updateSupplierShipmentsInBulk(@RequestBody List<UpdateSupplierShipmentDTO> shipmentDTOs) {
        List<SupplierShipment> supplierShipments = supplierShipmentService.updateSupplierShipmentsInBulk(shipmentDTOs);
        return ResponseEntity.ok(supplierShipments);
    }

    // TODO: Secure endpoint
//    @PreAuthorize("@securityService.canAccessOrganizationEntity(#shipmentIds.getFirst(), \"SupplierShipment\", \"Delete\")")
    @DeleteMapping("/delete/bulk")
    public ResponseEntity<List<Integer>> deleteSupplierShipmentsInBulk(@RequestBody List<Integer> shipmentIds) {
        supplierShipmentService.deleteSupplierShipmentsInBulk(shipmentIds);

        return ResponseEntity.ok().build();
    }
}

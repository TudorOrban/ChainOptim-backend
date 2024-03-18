package org.chainoptim.features.client.controller;

import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.features.client.service.ClientShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients/shipments")
public class ClientShipmentController {

    private final ClientShipmentService clientShipmentService;

    @Autowired
    public ClientShipmentController(ClientShipmentService clientShipmentService) {
        this.clientShipmentService = clientShipmentService;
    }

    @RequestMapping("/order/{orderId}")
    public ResponseEntity<List<ClientShipment>> getShipmentsByOrderId(@PathVariable("orderId") Integer orderId) {
        List<ClientShipment> shipments = clientShipmentService.getClientShipmentByClientOrderId(orderId);
        return ResponseEntity.ok(shipments);
    }
}

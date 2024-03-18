package org.chainoptim.features.client.controller;

import org.chainoptim.features.client.dto.CreateClientOrderDTO;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.client.service.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client-orders")
public class ClientOrderController {

    private final ClientOrderService clientOrderService;

    @Autowired
    public ClientOrderController(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<ClientOrder>> getClientsByOrganizationId(@PathVariable Integer organizationId) {
        List<ClientOrder> clientOrders = clientOrderService.getClientOrdersByOrganizationId(organizationId);
        return ResponseEntity.ok(clientOrders);
    }

    @PostMapping("/create")
    public ResponseEntity<ClientOrder> createClientOrder(@RequestBody CreateClientOrderDTO order) {
        ClientOrder clientOrder = clientOrderService.saveOrUpdateClientOrder(order);
        return ResponseEntity.ok(clientOrder);
    }
}

package org.chainoptim.features.client.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.client.dto.CreateClientOrderDTO;
import org.chainoptim.features.client.dto.UpdateClientOrderDTO;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.client.service.ClientOrderService;
import org.chainoptim.features.supplier.dto.SuppliersSearchDTO;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client-orders")
public class ClientOrderController {

    private final ClientOrderService clientOrderService;
    private final SecurityService securityService;

    @Autowired
    public ClientOrderController(ClientOrderService clientOrderService,
                                 SecurityService securityService) {
        this.clientOrderService = clientOrderService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Client\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<ClientOrder>> getClientOrdersByOrganizationId(@PathVariable Integer organizationId) {
        List<ClientOrder> clientOrders = clientOrderService.getClientOrdersByOrganizationId(organizationId);
        return ResponseEntity.ok(clientOrders);
    }

    @PreAuthorize("@securityService.canAccessEntity(#clientId, \"Client\", \"Read\")")
    @GetMapping("/client/advanced/{clientId}")
    public ResponseEntity<PaginatedResults<ClientOrder>> getClientOrdersByClientIdAdvanced(
            @PathVariable Integer clientId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<ClientOrder> clientOrders = clientOrderService.getClientOrdersByClientIdAdvanced(clientId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(clientOrders);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTO.getOrganizationId(), \"ClientOrder\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<ClientOrder> createClientOrder(@RequestBody CreateClientOrderDTO orderDTO) {
        ClientOrder clientOrder = clientOrderService.createClientOrder(orderDTO);
        return ResponseEntity.ok(clientOrder);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTOs.getFirst().getOrganizationId(), \"ClientOrder\", \"Create\")")
    @PostMapping("/create/bulk")
    public ResponseEntity<List<ClientOrder>> createClientOrdersInBulk(@RequestBody List<CreateClientOrderDTO> orderDTOs) {
        List<ClientOrder> clientOrders = clientOrderService.createClientOrdersInBulk(orderDTOs);
        return ResponseEntity.ok(clientOrders);
    }

    // Update
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#orderDTOs.getFirst().getOrganizationId(), \"ClientOrder\", \"Update\")")
    @PutMapping("/update/bulk")
    public ResponseEntity<List<ClientOrder>> updateClientOrdersInBulk(@RequestBody List<UpdateClientOrderDTO> orderDTOs) {
        List<ClientOrder> clientOrders = clientOrderService.updateClientOrdersInBulk(orderDTOs);
        return ResponseEntity.ok(clientOrders);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#orderIds.getFirst(), \"ClientOrder\", \"Delete\")") // Secure as service method ensures all orders belong to the same organization
    @DeleteMapping("/delete/bulk")
    public ResponseEntity<List<Integer>> deleteClientOrdersInBulk(@RequestBody List<Integer> orderIds) {
        clientOrderService.deleteClientOrdersInBulk(orderIds);

        return ResponseEntity.ok().build();
    }
}

package org.chainoptim.features.client.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.features.client.service.ClientShipmentService;
import org.chainoptim.features.client.dto.CreateClientShipmentDTO;
import org.chainoptim.features.client.dto.UpdateClientShipmentDTO;
import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.features.client.service.ClientShipmentService;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client-shipments")
public class ClientShipmentController {

    private final ClientShipmentService clientShipmentService;
    private final SecurityService securityService;

    @Autowired
    public ClientShipmentController(ClientShipmentService clientShipmentService,
                                      SecurityService securityService) {
        this.clientShipmentService = clientShipmentService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canAccessEntity(#clientOrderId, \"ClientOrder\", \"Read\")")
    @GetMapping("/order/{clientOrderId}")
    public ResponseEntity<List<ClientShipment>> getClientShipmentsByClientOrderId(@PathVariable("clientOrderId") Integer clientOrderId) {
        List<ClientShipment> clientShipments = clientShipmentService.getClientShipmentByClientOrderId(clientOrderId);
        return ResponseEntity.ok(clientShipments);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Organization\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<ClientShipment>> getClientShipmentsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "filters", required = false, defaultValue = "") String filtersJson,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        SearchParams searchParams = new SearchParams(searchQuery, filtersJson, null, sortBy, ascending, page, itemsPerPage);
        PaginatedResults<ClientShipment> clientShipments = clientShipmentService.getClientShipmentsAdvanced(SearchMode.ORGANIZATION, organizationId, searchParams);
        return ResponseEntity.ok(clientShipments);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#shipmentDTO.getOrganizationId(), \"Client\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<ClientShipment> createClientShipment(@RequestBody CreateClientShipmentDTO shipmentDTO) {
        ClientShipment clientShipment = clientShipmentService.createClientShipment(shipmentDTO);
        return ResponseEntity.ok(clientShipment);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#shipmentDTOs.getFirst().getOrganizationId(), \"Client\", \"Create\")")
    @PostMapping("/create/bulk")
    public ResponseEntity<List<ClientShipment>> createClientShipmentsInBulk(@RequestBody List<CreateClientShipmentDTO> shipmentDTOs) {
        List<ClientShipment> clientShipments = clientShipmentService.createClientShipmentsInBulk(shipmentDTOs);
        return ResponseEntity.ok(clientShipments);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#shipmentDTOs.getFirst().getOrganizationId(), \"Client\", \"Update\")")
    @PutMapping("/update/bulk")
    public ResponseEntity<List<ClientShipment>> updateClientShipmentsInBulk(@RequestBody List<UpdateClientShipmentDTO> shipmentDTOs) {
        List<ClientShipment> clientShipments = clientShipmentService.updateClientShipmentsInBulk(shipmentDTOs);
        return ResponseEntity.ok(clientShipments);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#shipmentIds.getFirst(), \"ClientShipment\", \"Delete\")")
    @DeleteMapping("/delete/bulk")
    public ResponseEntity<List<Integer>> deleteClientShipmentsInBulk(@RequestBody List<Integer> shipmentIds) {
        clientShipmentService.deleteClientShipmentsInBulk(shipmentIds);

        return ResponseEntity.ok().build();
    }
}
package org.chainoptim.features.demand.controller;

import org.chainoptim.core.general.security.service.SecurityService;
import org.chainoptim.features.demand.dto.ClientOverviewDTO;
import org.chainoptim.features.demand.dto.ClientsSearchDTO;
import org.chainoptim.features.demand.dto.CreateClientDTO;
import org.chainoptim.features.demand.dto.UpdateClientDTO;
import org.chainoptim.features.demand.model.Client;
import org.chainoptim.features.demand.service.ClientService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final SecurityService securityService;

    @Autowired
    public ClientController(
            ClientService clientService,
            SecurityService securityService

    ) {
        this.clientService = clientService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Client\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Client>> getClientsByOrganizationId(@PathVariable Integer organizationId) {
        List<Client> clients = clientService.getClientsByOrganizationId(organizationId);
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Client\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<ClientsSearchDTO>> getClientsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<ClientsSearchDTO> clients = clientService.getClientsByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(clients);
    }

    @PreAuthorize("@securityService.canAccessEntity(#clientId, \"Client\", \"Read\")")
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer clientId) {
        Client client = clientService.getClientById(clientId);
        return ResponseEntity.ok(client);
    }

    @PreAuthorize("@securityService.canAccessEntity(#clientId, \"Client\", \"Read\")")
    @GetMapping("/{clientId}/overview")
    public ResponseEntity<ClientOverviewDTO> getClientOverview(@PathVariable Integer clientId) {
        ClientOverviewDTO overviewDTO = clientService.getClientOverview(clientId);
        return ResponseEntity.ok(overviewDTO);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#clientDTO.getOrganizationId(), \"Client\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody CreateClientDTO clientDTO) {
        Client client = clientService.createClient(clientDTO);
        return ResponseEntity.ok(client);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#clientDTO.getId(), \"Client\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody UpdateClientDTO clientDTO) {
        Client client = clientService.updateClient(clientDTO);
        return ResponseEntity.ok(client);
    }

    @PreAuthorize("@securityService.canAccessEntity(#clientId, \"Client\", \"Delete\")")
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<Integer> deleteClient(@PathVariable Integer clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.ok(clientId);
    }
}
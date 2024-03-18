package org.chainoptim.features.client.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.client.dto.ClientsSearchDTO;
import org.chainoptim.features.client.dto.CreateClientDTO;
import org.chainoptim.features.client.dto.UpdateClientDTO;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.service.ClientService;
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
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId)")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Client>> getClientsByOrganizationId(@PathVariable Integer organizationId) {
        List<Client> clients = clientService.getClientsByOrganizationId(organizationId);
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId)")
    @GetMapping("/organizations/advanced/{organizationId}")
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

    @PreAuthorize("@securityService.canAccessEntity(#clientId, \"Client\")")
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId) {
        Client client = clientService.getClientById(clientId);
        return ResponseEntity.ok(client);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#clientDTO.getOrganizationId())")
    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody CreateClientDTO clientDTO) {
        Client client = clientService.createClient(clientDTO);
        return ResponseEntity.ok(client);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#clientDTO.getId(), \"Client\")")
    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody UpdateClientDTO clientDTO) {
        Client client = clientService.updateClient(clientDTO);
        return ResponseEntity.ok(client);
    }

    @PreAuthorize("@securityService.canAccessEntity(#clientId, \"Client\")")
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.ok().build();
    }
}
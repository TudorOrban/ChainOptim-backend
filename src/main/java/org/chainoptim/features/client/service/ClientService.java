package org.chainoptim.features.client.service;

import org.chainoptim.features.client.dto.ClientsSearchDTO;
import org.chainoptim.features.client.dto.CreateClientDTO;
import org.chainoptim.features.client.dto.UpdateClientDTO;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface ClientService {
    // Fetch
    List<Client> getClientsByOrganizationId(Integer organizationId);
    PaginatedResults<ClientsSearchDTO> getClientsByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    Client getClientById(Integer id);

    // Create
    Client createClient(CreateClientDTO clientDTO);

    // Update
    Client updateClient(UpdateClientDTO updateClientDTO);

    // Delete
    void deleteClient(Integer clientId);
}

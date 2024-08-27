package org.chainoptim.features.demand.client.service;

import org.chainoptim.features.demand.client.dto.ClientOverviewDTO;
import org.chainoptim.features.demand.client.dto.ClientsSearchDTO;
import org.chainoptim.features.demand.client.dto.CreateClientDTO;
import org.chainoptim.features.demand.client.dto.UpdateClientDTO;
import org.chainoptim.features.demand.client.model.Client;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface ClientService {
    // Fetch
    List<Client> getClientsByOrganizationId(Integer organizationId);
    PaginatedResults<ClientsSearchDTO> getClientsByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    Client getClientById(Integer id);
    ClientOverviewDTO getClientOverview(Integer clientId);

    // Create
    Client createClient(CreateClientDTO clientDTO);

    // Update
    Client updateClient(UpdateClientDTO updateClientDTO);

    // Delete
    void deleteClient(Integer clientId);
}

package org.chainoptim.features.demand.service;

import org.chainoptim.features.demand.dto.ClientOverviewDTO;
import org.chainoptim.features.demand.dto.ClientsSearchDTO;
import org.chainoptim.features.demand.dto.CreateClientDTO;
import org.chainoptim.features.demand.dto.UpdateClientDTO;
import org.chainoptim.features.demand.model.Client;
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

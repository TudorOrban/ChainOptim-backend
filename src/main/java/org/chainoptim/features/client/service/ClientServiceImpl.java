package org.chainoptim.features.client.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.client.dto.ClientDTOMapper;
import org.chainoptim.features.client.dto.ClientsSearchDTO;
import org.chainoptim.features.client.dto.CreateClientDTO;
import org.chainoptim.features.client.dto.UpdateClientDTO;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EntitySanitizerService entitySanitizerService) {
        this.clientRepository = clientRepository;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + clientId + " not found."));
    }

    public List<Client> getClientsByOrganizationId(Integer organizationId) {
        return clientRepository.findByOrganizationId(organizationId);
    }

    public PaginatedResults<ClientsSearchDTO> getClientsByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Client> paginatedResults = clientRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
            paginatedResults.results.stream()
            .map(ClientDTOMapper::convertToClientsSearchDTO)
            .toList(),
            paginatedResults.totalCount
        );
    }

    public Client createClient(CreateClientDTO clientDTO) {
        CreateClientDTO sanitizedClientDTO = entitySanitizerService.sanitizeCreateClientDTO(clientDTO);
        return clientRepository.save(ClientDTOMapper.convertCreateClientDTOToClient(sanitizedClientDTO));
    }

    public Client updateClient(UpdateClientDTO clientDTO) {
        UpdateClientDTO sanitizedClientDTO = entitySanitizerService.sanitizeUpdateClientDTO(clientDTO);
        Client client = clientRepository.findById(Long.valueOf(sanitizedClientDTO.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + sanitizedClientDTO.getId() + " not found."));

        client.setName(sanitizedClientDTO.getName());

        clientRepository.save(client);
        return client;
    }

    public void deleteClient(Integer clientId) {
        Client client = new Client();
        client.setId(clientId);
        clientRepository.delete(client);
    }
}

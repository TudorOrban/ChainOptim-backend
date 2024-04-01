package org.chainoptim.features.client.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.client.dto.ClientDTOMapper;
import org.chainoptim.features.client.dto.ClientsSearchDTO;
import org.chainoptim.features.client.dto.CreateClientDTO;
import org.chainoptim.features.client.dto.UpdateClientDTO;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.repository.ClientRepository;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.service.LocationService;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final LocationService locationService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                                LocationService locationService,
                             EntitySanitizerService entitySanitizerService) {
        this.clientRepository = clientRepository;
        this.locationService = locationService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Integer clientId) {
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

        // Create location if requested
        if (sanitizedClientDTO.isCreateLocation() && sanitizedClientDTO.getLocation() != null) {
            Location location = locationService.createLocation(sanitizedClientDTO.getLocation());
            Client client = ClientDTOMapper.convertCreateClientDTOToClient(sanitizedClientDTO);
            client.setLocation(location);
            return clientRepository.save(client);
        } else {
            return clientRepository.save(ClientDTOMapper.convertCreateClientDTOToClient((sanitizedClientDTO)));
        }
    }

    public Client updateClient(UpdateClientDTO clientDTO) {
        UpdateClientDTO sanitizedClientDTO = entitySanitizerService.sanitizeUpdateClientDTO(clientDTO);
        Client client = clientRepository.findById(sanitizedClientDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + sanitizedClientDTO.getId() + " not found."));

        client.setName(sanitizedClientDTO.getName());

        // Create new location or use existing or throw if not provided
        Location location;
        if (sanitizedClientDTO.isCreateLocation() && sanitizedClientDTO.getLocation() != null) {
            location = locationService.createLocation(sanitizedClientDTO.getLocation());
        } else if (sanitizedClientDTO.getLocationId() != null){
            location = new Location();
            location.setId(sanitizedClientDTO.getLocationId());
        } else {
            throw new ValidationException("Location is required.");
        }
        client.setLocation(location);

        clientRepository.save(client);
        return client;
    }

    public void deleteClient(Integer clientId) {
        Client client = new Client();
        client.setId(clientId);
        clientRepository.delete(client);
    }
}

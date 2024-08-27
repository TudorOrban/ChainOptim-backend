package org.chainoptim.features.demand.client.service;

import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.demand.client.dto.*;
import org.chainoptim.features.demand.client.model.Client;
import org.chainoptim.features.demand.client.repository.ClientRepository;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.service.LocationService;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final LocationService locationService;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             LocationService locationService,
                             SubscriptionPlanLimiterService planLimiterService,
                             EntitySanitizerService entitySanitizerService) {
        this.clientRepository = clientRepository;
        this.locationService = locationService;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
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

    public Client getClientById(Integer clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID: " + clientId + " not found."));
    }

    public ClientOverviewDTO getClientOverview(Integer clientId) {
        List<SmallEntityDTO> suppliedProducts = clientRepository.findSuppliedProductsByClientId(clientId);
        List<SmallEntityDTO> deliveredFromFactories = clientRepository.findDeliveredToFactoriesByClientId(clientId);
        List<SmallEntityDTO> deliveredFromWarehouses = clientRepository.findDeliveredToWarehousesByClientId(clientId);

        return new ClientOverviewDTO(suppliedProducts, deliveredFromFactories, deliveredFromWarehouses);
    }

    // Create
    public Client createClient(CreateClientDTO clientDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(clientDTO.getOrganizationId(), Feature.CLIENT, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed clients for the current Subscription Plan.");
        }

        // Sanitize input
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

    // Update
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

    // Delete
    @Transactional
    public void deleteClient(Integer clientId) {
        Client client = new Client();
        client.setId(clientId);
        clientRepository.delete(client);
    }
}

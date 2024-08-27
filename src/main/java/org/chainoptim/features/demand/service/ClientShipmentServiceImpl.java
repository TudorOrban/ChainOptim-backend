package org.chainoptim.features.demand.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.demand.dto.CreateClientShipmentDTO;
import org.chainoptim.features.demand.dto.ClientDTOMapper;
import org.chainoptim.features.demand.dto.UpdateClientShipmentDTO;
import org.chainoptim.features.demand.model.ClientShipment;
import org.chainoptim.features.demand.repository.ClientShipmentRepository;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.transaction.Transactional;
import org.chainoptim.shared.search.model.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClientShipmentServiceImpl implements ClientShipmentService {

    private final ClientShipmentRepository clientShipmentRepository;
    private final LocationRepository locationRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;


    @Autowired
    public ClientShipmentServiceImpl(ClientShipmentRepository clientShipmentRepository,
                                     LocationRepository locationRepository,
                                     SubscriptionPlanLimiterService planLimiterService,
                                     EntitySanitizerService entitySanitizerService) {
        this.clientShipmentRepository = clientShipmentRepository;
        this.locationRepository = locationRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<ClientShipment> getClientShipmentByClientId(Integer clientId) {
        return clientShipmentRepository.findByClientId(clientId);
    }

    public ClientShipment getClientShipmentById(Integer shipmentId) {
        return clientShipmentRepository.findById(shipmentId).orElseThrow(() -> new ResourceNotFoundException("Client shipment with ID: " + shipmentId + " not found."));
    }

    public PaginatedResults<ClientShipment> getClientShipmentsAdvanced(SearchMode searchMode, Integer entityId, SearchParams searchParams) {
        // Attempt to parse filters JSON
        Map<String, String> filters;
        if(!searchParams.getFiltersJson().isEmpty()) {
            try {
                filters = new ObjectMapper().readValue(searchParams.getFiltersJson(), new TypeReference<Map<String, String>>(){});
                searchParams.setFilters(filters);
            } catch (JsonProcessingException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid format");
            }
        }
        return clientShipmentRepository.findByClientIdAdvanced(searchMode, entityId, searchParams);
    }

    // Create
    public ClientShipment createClientShipment(CreateClientShipmentDTO shipmentDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(shipmentDTO.getOrganizationId(), Feature.CLIENT_SHIPMENT, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Client Shipments for the current Subscription Plan.");
        }

        // Sanitize input and map to entity
        CreateClientShipmentDTO sanitizedShipmentDTO = entitySanitizerService.sanitizeCreateClientShipmentDTO(shipmentDTO);
        ClientShipment clientShipment = ClientDTOMapper.mapCreateClientShipmentDTOTOShipment(sanitizedShipmentDTO);

        if (sanitizedShipmentDTO.getSourceLocationId() != null) {
            Location sourceLocation = locationRepository.findById(sanitizedShipmentDTO.getSourceLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Source location with ID: " + sanitizedShipmentDTO.getSourceLocationId() + " not found"));
            clientShipment.setSourceLocation(sourceLocation);
        }

        if (sanitizedShipmentDTO.getDestinationLocationId() != null) {
            Location destinationLocation = locationRepository.findById(sanitizedShipmentDTO.getDestinationLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Destination location with ID: " + sanitizedShipmentDTO.getDestinationLocationId() + " not found"));
            clientShipment.setDestinationLocation(destinationLocation);
        }

        return clientShipmentRepository.save(clientShipment);
    }

    @Transactional
    public List<ClientShipment> createClientShipmentsInBulk(List<CreateClientShipmentDTO> shipmentDTOs) {
        // Ensure same organizationId
        if (shipmentDTOs.stream().map(CreateClientShipmentDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All shipments must belong to the same organization.");
        }
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(shipmentDTOs.getFirst().getOrganizationId(), Feature.CLIENT_SHIPMENT, shipmentDTOs.size())) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Client Shipments for the current Subscription Plan.");
        }

        // Sanitize and map to entity
        List<ClientShipment> shipments = shipmentDTOs.stream()
                .map(shipmentDTO -> {
                    CreateClientShipmentDTO sanitizedShipmentDTO = entitySanitizerService.sanitizeCreateClientShipmentDTO(shipmentDTO);
                    return ClientDTOMapper.mapCreateClientShipmentDTOTOShipment(sanitizedShipmentDTO);
                })
                .toList();

        return clientShipmentRepository.saveAll(shipments);
    }

    @Transactional
    public List<ClientShipment> updateClientShipmentsInBulk(List<UpdateClientShipmentDTO> shipmentDTOs) {
        List<ClientShipment> shipments = new ArrayList<>();
        for (UpdateClientShipmentDTO shipmentDTO : shipmentDTOs) {
            ClientShipment shipment = clientShipmentRepository.findById(shipmentDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client Shipment with ID: " + shipmentDTO.getId() + " not found."));

            ClientDTOMapper.setUpdateClientShipmentDTOToClientShipment(shipment, shipmentDTO);
            shipments.add(shipment);
        }

        return clientShipmentRepository.saveAll(shipments);
    }

    @Transactional
    public List<Integer> deleteClientShipmentsInBulk(List<Integer> shipmentIds) {
        List<ClientShipment> shipments = clientShipmentRepository.findAllById(shipmentIds);

        clientShipmentRepository.deleteAll(shipments);

        return shipmentIds;
    }
}

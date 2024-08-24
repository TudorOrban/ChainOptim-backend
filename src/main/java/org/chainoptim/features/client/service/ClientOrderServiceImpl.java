package org.chainoptim.features.client.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.core.upcomingevents.service.UpcomingEventProcessorService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.client.dto.ClientDTOMapper;
import org.chainoptim.features.client.dto.CreateClientOrderDTO;
import org.chainoptim.features.client.dto.UpdateClientOrderDTO;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.client.model.ClientOrderEvent;
import org.chainoptim.features.client.repository.ClientOrderRepository;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.sanitization.EntitySanitizerService;

import org.chainoptim.shared.search.model.PaginatedResults;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.chainoptim.shared.search.model.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientOrderServiceImpl implements ClientOrderService {

    private final ClientOrderRepository clientOrderRepository;
    private final KafkaClientOrderService kafkaClientOrderService;
    private final UpcomingEventProcessorService upcomingEventProcessorService;
    private final ProductRepository productRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ClientOrderServiceImpl(
            ClientOrderRepository clientOrderRepository,
            KafkaClientOrderService kafkaClientOrderService,
            UpcomingEventProcessorService upcomingEventProcessorService,
            ProductRepository productRepository,
            SubscriptionPlanLimiterService planLimiterService,
            EntitySanitizerService entitySanitizerService
    ) {
        this.clientOrderRepository = clientOrderRepository;
        this.kafkaClientOrderService = kafkaClientOrderService;
        this.upcomingEventProcessorService = upcomingEventProcessorService;
        this.productRepository = productRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public List<ClientOrder> getClientOrdersByOrganizationId(Integer organizationId) {
        return clientOrderRepository.findByOrganizationId(organizationId);
    }

    public List<ClientOrder> getClientOrdersByClientId(Integer clientId) {
        return clientOrderRepository.findByClientId(clientId);
    }

    public PaginatedResults<ClientOrder> getClientOrdersAdvanced(SearchMode searchMode, Integer entityId, SearchParams searchParams) {
        // Attempt to parse filters JSON
        Map<String, String> filters;
        if (!searchParams.getFiltersJson().isEmpty()) {
            try {
                filters = new ObjectMapper().readValue(searchParams.getFiltersJson(), new TypeReference<Map<String, String>>(){});
                searchParams.setFilters(filters);
            } catch (JsonProcessingException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filters format");
            }
        }

        return clientOrderRepository.findByClientIdAdvanced(searchMode, entityId, searchParams);
    }

    // Create
    public ClientOrder createClientOrder(CreateClientOrderDTO orderDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(orderDTO.getOrganizationId(), Feature.CLIENT_ORDER, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed clients for the current Subscription Plan.");
        }

        // Sanitize input and map to entity
        CreateClientOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateClientOrderDTO(orderDTO);
        ClientOrder clientOrder = ClientDTOMapper.mapCreateDtoToClientOrder(sanitizedOrderDTO);
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + orderDTO.getProductId() + " not found."));
        clientOrder.setProduct(product);

        ClientOrder savedOrder = clientOrderRepository.save(clientOrder);

        // Publish order to upcoming events
        upcomingEventProcessorService.processUpcomingEvent(savedOrder);

        // Publish order to Kafka broker
        kafkaClientOrderService.sendClientOrderEvent(
                new ClientOrderEvent(savedOrder, null, KafkaEvent.EventType.CREATE, savedOrder.getClientId(), Feature.CLIENT, "Test"));

        return savedOrder;
    }

    @Transactional
    public List<ClientOrder> createClientOrdersInBulk(List<CreateClientOrderDTO> orderDTOs) {
        // Ensure same organizationId
        if (orderDTOs.stream().map(CreateClientOrderDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All orders must belong to the same organization.");
        }
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(orderDTOs.getFirst().getOrganizationId(), Feature.CLIENT_ORDER, orderDTOs.size())) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Client Orders for the current Subscription Plan.");
        }

        // Sanitize and map to entity
        List<ClientOrder> orders = orderDTOs.stream()
                .map(orderDTO -> {
                    CreateClientOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateClientOrderDTO(orderDTO);
                    ClientOrder clientOrder = ClientDTOMapper.mapCreateDtoToClientOrder(sanitizedOrderDTO);
                    Product product = productRepository.findById(sanitizedOrderDTO.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + sanitizedOrderDTO.getProductId() + " not found."));
                    clientOrder.setProduct(product);
                    return clientOrder;
                })
                .toList();

        List<ClientOrder> savedOrders = clientOrderRepository.saveAll(orders);

        // Publish order to upcoming events
        for (ClientOrder order: savedOrders) {
            upcomingEventProcessorService.processUpcomingEvent(order);
        }

        // Publish order events to Kafka broker
        List<ClientOrderEvent> orderEvents = new ArrayList<>();
        orders.stream()
                .map(order -> new ClientOrderEvent(order, null, KafkaEvent.EventType.CREATE, order.getClientId(), Feature.CLIENT, "Test"))
                .forEach(orderEvents::add);

        kafkaClientOrderService.sendClientOrderEventsInBulk(orderEvents);

        return savedOrders;
    }

    @Transactional
    public List<ClientOrder> updateClientOrdersInBulk(List<UpdateClientOrderDTO> orderDTOs) {
        List<ClientOrder> orders = clientOrderRepository.findByIds(orderDTOs.stream().map(UpdateClientOrderDTO::getId).toList())
                .orElseThrow(() -> new ResourceNotFoundException("Client Orders not found."));

        // Save old orders for event publishing
        Map<Integer, ClientOrder> oldOrders = new HashMap<>();
        for (ClientOrder order : orders) {
            oldOrders.put(order.getId(), order.deepCopy());
        }

        // Update orders
        List<ClientOrder> updatedOrders = orders.stream()
                .map(order -> {
                    UpdateClientOrderDTO orderDTO = orderDTOs.stream()
                            .filter(dto -> dto.getId().equals(order.getId()))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Client Order with ID: " + order.getId() + " not found."));
                    UpdateClientOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeUpdateClientOrderDTO(orderDTO);
                    ClientDTOMapper.setUpdateClientOrderDTOToClientOrder(order, sanitizedOrderDTO);
                    Product product = productRepository.findById(sanitizedOrderDTO.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + sanitizedOrderDTO.getProductId() + " not found."));
                    order.setProduct(product);
                    return order;
                }).toList();

        // Save
        List<ClientOrder> savedOrders = clientOrderRepository.saveAll(updatedOrders);

        // Publish order events to Kafka broker
        List<ClientOrderEvent> orderEvents = new ArrayList<>();
        savedOrders.stream()
                .map(order -> {
                    ClientOrder oldOrder = oldOrders.get(order.getId());
                    return new ClientOrderEvent(order, oldOrder, KafkaEvent.EventType.UPDATE, order.getClientId(), Feature.CLIENT, "Test");
                })
                .forEach(orderEvents::add);

        kafkaClientOrderService.sendClientOrderEventsInBulk(orderEvents);

        return savedOrders;
    }
    
    @Transactional
    public List<Integer> deleteClientOrdersInBulk(List<java.lang.Integer> orderIds) {
        List<ClientOrder> orders = clientOrderRepository.findAllById(orderIds);
        // Ensure same organizationId
        if (orders.stream().map(ClientOrder::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All orders must belong to the same organization.");
        }

        clientOrderRepository.deleteAll(orders);

        // Publish order events to Kafka broker
        List<ClientOrderEvent> orderEvents = new ArrayList<>();
        orders.stream()
                .map(order -> new ClientOrderEvent(null, order, KafkaEvent.EventType.CREATE, order.getClientId(), Feature.CLIENT, "Test"))
                .forEach(orderEvents::add);

        kafkaClientOrderService.sendClientOrderEventsInBulk(orderEvents);

        return orderIds;
    }
}

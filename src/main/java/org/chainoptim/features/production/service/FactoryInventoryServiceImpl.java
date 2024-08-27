package org.chainoptim.features.production.service;

import org.chainoptim.core.overview.notifications.model.KafkaEvent;
import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.production.dto.FactoryDTOMapper;
import org.chainoptim.features.production.dto.CreateFactoryInventoryItemDTO;
import org.chainoptim.features.production.dto.UpdateFactoryInventoryItemDTO;
import org.chainoptim.features.production.model.FactoryInventoryItem;
import org.chainoptim.features.production.model.FactoryInventoryItemEvent;
import org.chainoptim.features.production.repository.FactoryInventoryItemRepository;
import org.chainoptim.features.goods.product.model.Product;
import org.chainoptim.features.goods.product.repository.ProductRepository;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.features.goods.component.repository.ComponentRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class FactoryInventoryServiceImpl implements FactoryInventoryService {

    private final FactoryInventoryItemRepository factoryInventoryItemRepository;
    private final KafkaFactoryInventoryService kafkaFactoryInventoryService;
    private final ProductRepository productRepository;
    private final ComponentRepository componentRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public FactoryInventoryServiceImpl(
            FactoryInventoryItemRepository factoryInventoryItemRepository,
            KafkaFactoryInventoryService kafkaFactoryInventoryService,
            ProductRepository productRepository,
            ComponentRepository componentRepository,
            SubscriptionPlanLimiterService planLimiterService,
            EntitySanitizerService entitySanitizerService
    ) {
        this.factoryInventoryItemRepository = factoryInventoryItemRepository;
        this.kafkaFactoryInventoryService = kafkaFactoryInventoryService;
        this.productRepository = productRepository;
        this.componentRepository = componentRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public List<FactoryInventoryItem> getFactoryInventoryItemsByOrganizationId(Integer organizationId) {
        return factoryInventoryItemRepository.findByOrganizationId(organizationId);
    }

    public List<FactoryInventoryItem> getFactoryInventoryItemsByFactoryId(Integer factoryId) {
        return factoryInventoryItemRepository.findByFactoryId(factoryId);
    }

    public PaginatedResults<FactoryInventoryItem> getFactoryInventoryItemsAdvanced(SearchMode searchMode, Integer entityId, SearchParams searchParams) {
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

        return factoryInventoryItemRepository.findByFactoryIdAdvanced(searchMode, entityId, searchParams);
    }

    // Create
    public FactoryInventoryItem createFactoryInventoryItem(CreateFactoryInventoryItemDTO orderDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(orderDTO.getOrganizationId(), Feature.FACTORY_INVENTORY, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed factories for the current Subscription Plan.");
        }

        // Sanitize input and map to entity
        CreateFactoryInventoryItemDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateFactoryInventoryItemDTO(orderDTO);
        FactoryInventoryItem factoryInventoryItem = FactoryDTOMapper.mapCreateFactoryInventoryItemDTOToFactoryInventoryItem(sanitizedOrderDTO);
        if (sanitizedOrderDTO.getProductId() != null) {
            Product product = productRepository.findById(orderDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + orderDTO.getProductId() + " not found."));
            factoryInventoryItem.setProduct(product);
        }
        if (sanitizedOrderDTO.getComponentId() != null) {
            Component component = componentRepository.findById(orderDTO.getComponentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + orderDTO.getComponentId() + " not found."));
            factoryInventoryItem.setComponent(component);
        }
        FactoryInventoryItem savedOrder = factoryInventoryItemRepository.save(factoryInventoryItem);

        // Publish order to Kafka broker
        kafkaFactoryInventoryService.sendFactoryInventoryItemEvent(
                new FactoryInventoryItemEvent(savedOrder, null, KafkaEvent.EventType.CREATE, savedOrder.getFactoryId(), Feature.CLIENT, "Test"));

        return savedOrder;
    }

    @Transactional
    public List<FactoryInventoryItem> createFactoryInventoryItemsInBulk(List<CreateFactoryInventoryItemDTO> orderDTOs) {
        // Ensure same organizationId
        if (orderDTOs.stream().map(CreateFactoryInventoryItemDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All orders must belong to the same organization.");
        }
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(orderDTOs.getFirst().getOrganizationId(), Feature.CLIENT_ORDER, orderDTOs.size())) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Factory Orders for the current Subscription Plan.");
        }

        // Sanitize and map to entity
        List<FactoryInventoryItem> orders = orderDTOs.stream()
                .map(orderDTO -> {
                    CreateFactoryInventoryItemDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateFactoryInventoryItemDTO(orderDTO);
                    FactoryInventoryItem factoryInventoryItem = FactoryDTOMapper.mapCreateFactoryInventoryItemDTOToFactoryInventoryItem(sanitizedOrderDTO);
                    if (sanitizedOrderDTO.getProductId() != null) {
                        Product product = productRepository.findById(orderDTO.getProductId())
                                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + orderDTO.getProductId() + " not found."));
                        factoryInventoryItem.setProduct(product);
                    }
                    if (sanitizedOrderDTO.getComponentId() != null) {
                        Component component = componentRepository.findById(orderDTO.getComponentId())
                                .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + orderDTO.getComponentId() + " not found."));
                        factoryInventoryItem.setComponent(component);
                    }
                    return factoryInventoryItem;
                })
                .toList();

        List<FactoryInventoryItem> savedOrders = factoryInventoryItemRepository.saveAll(orders);

        // Publish order events to Kafka broker
        List<FactoryInventoryItemEvent> orderEvents = new ArrayList<>();
        orders.stream()
                .map(order -> new FactoryInventoryItemEvent(order, null, KafkaEvent.EventType.CREATE, order.getFactoryId(), Feature.CLIENT, "Test"))
                .forEach(orderEvents::add);

        kafkaFactoryInventoryService.sendFactoryInventoryItemEventsInBulk(orderEvents);

        return savedOrders;
    }

    @Transactional
    public List<FactoryInventoryItem> updateFactoryInventoryItemsInBulk(List<UpdateFactoryInventoryItemDTO> orderDTOs) {
        List<FactoryInventoryItem> orders = factoryInventoryItemRepository.findByIds(orderDTOs.stream().map(UpdateFactoryInventoryItemDTO::getId).toList())
                .orElseThrow(() -> new ResourceNotFoundException("Factory Orders not found."));

        // Save old orders for event publishing
        Map<Integer, FactoryInventoryItem> oldOrders = new HashMap<>();
        for (FactoryInventoryItem order : orders) {
            oldOrders.put(order.getId(), order.deepCopy());
        }

        // Update orders
        List<FactoryInventoryItem> updatedOrders = orders.stream()
                .map(item -> {
                    UpdateFactoryInventoryItemDTO orderDTO = orderDTOs.stream()
                            .filter(dto -> dto.getId().equals(item.getId()))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Factory Order with ID: " + item.getId() + " not found."));
                    UpdateFactoryInventoryItemDTO sanitizedOrderDTO = entitySanitizerService.sanitizeUpdateFactoryInventoryItemDTO(orderDTO);
                    FactoryDTOMapper.setUpdateFactoryInventoryItemDTOToFactoryInventoryItem(item, sanitizedOrderDTO);
                    if (sanitizedOrderDTO.getProductId() != null) {
                        Product product = productRepository.findById(orderDTO.getProductId())
                                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + orderDTO.getProductId() + " not found."));
                        item.setProduct(product);
                    }
                    if (sanitizedOrderDTO.getComponentId() != null) {
                        Component component = componentRepository.findById(orderDTO.getComponentId())
                                .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + orderDTO.getComponentId() + " not found."));
                        item.setComponent(component);
                    }
                    return item;
                }).toList();

        // Save
        List<FactoryInventoryItem> savedOrders = factoryInventoryItemRepository.saveAll(updatedOrders);

        // Publish order events to Kafka broker
        List<FactoryInventoryItemEvent> orderEvents = new ArrayList<>();
        savedOrders.stream()
                .map(order -> {
                    FactoryInventoryItem oldOrder = oldOrders.get(order.getId());
                    return new FactoryInventoryItemEvent(order, oldOrder, KafkaEvent.EventType.UPDATE, order.getFactoryId(), Feature.CLIENT, "Test");
                })
                .forEach(orderEvents::add);

        kafkaFactoryInventoryService.sendFactoryInventoryItemEventsInBulk(orderEvents);

        return savedOrders;
    }

    @Transactional
    public List<Integer> deleteFactoryInventoryItemsInBulk(List<Integer> orderIds) {
        List<FactoryInventoryItem> orders = factoryInventoryItemRepository.findAllById(orderIds);
        // Ensure same organizationId
        if (orders.stream().map(FactoryInventoryItem::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All orders must belong to the same organization.");
        }

        factoryInventoryItemRepository.deleteAll(orders);

        // Publish order events to Kafka broker
        List<FactoryInventoryItemEvent> orderEvents = new ArrayList<>();
        orders.stream()
                .map(order -> new FactoryInventoryItemEvent(null, order, KafkaEvent.EventType.CREATE, order.getFactoryId(), Feature.CLIENT, "Test"))
                .forEach(orderEvents::add);

        kafkaFactoryInventoryService.sendFactoryInventoryItemEventsInBulk(orderEvents);

        return orderIds;
    }
}

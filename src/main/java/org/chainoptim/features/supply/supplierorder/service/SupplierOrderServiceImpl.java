package org.chainoptim.features.supply.supplierorder.service;

import org.chainoptim.core.overview.notifications.model.KafkaEvent;
import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.core.overview.upcomingevents.service.UpcomingEventProcessorService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.features.goods.component.repository.ComponentRepository;
import org.chainoptim.features.supply.supplierorder.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supply.supplier.dto.SupplierDTOMapper;
import org.chainoptim.features.supply.supplierorder.dto.UpdateSupplierOrderDTO;
import org.chainoptim.features.supply.supplierorder.model.SupplierOrder;
import org.chainoptim.features.supply.supplierorder.model.SupplierOrderEvent;
import org.chainoptim.features.supply.supplierorder.repository.SupplierOrderRepository;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final KafkaSupplierOrderService kafkaSupplierOrderService;
    private final UpcomingEventProcessorService upcomingEventProcessorService;
    private final ComponentRepository componentRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public SupplierOrderServiceImpl(
            SupplierOrderRepository supplierOrderRepository,
            KafkaSupplierOrderService kafkaSupplierOrderService,
            UpcomingEventProcessorService upcomingEventProcessorService,
            ComponentRepository componentRepository,
            SubscriptionPlanLimiterService planLimiterService,
            EntitySanitizerService entitySanitizerService
    ) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.kafkaSupplierOrderService = kafkaSupplierOrderService;
        this.upcomingEventProcessorService = upcomingEventProcessorService;
        this.componentRepository = componentRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<SupplierOrder> getSupplierOrdersByOrganizationId(Integer organizationId) {
        return supplierOrderRepository.findByOrganizationId(organizationId);
    }

    public List<SupplierOrder> getSupplierOrdersBySupplierId(Integer supplierId) {
        return supplierOrderRepository.findBySupplierId(supplierId);
    }

    public PaginatedResults<SupplierOrder> getSupplierOrdersAdvanced(SearchMode searchMode, Integer entityId, SearchParams searchParams) {
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

        return supplierOrderRepository.findBySupplierIdAdvanced(searchMode, entityId, searchParams);
    }

    // Create
    public SupplierOrder createSupplierOrder(CreateSupplierOrderDTO orderDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(orderDTO.getOrganizationId(), Feature.SUPPLIER_ORDER, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Supplier Orders for the current Subscription Plan.");
        }

        // Sanitize input and map to entity
        CreateSupplierOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateSupplierOrderDTO(orderDTO);
        SupplierOrder supplierOrder = SupplierDTOMapper.mapCreateDtoToSupplierOrder(sanitizedOrderDTO);
        Component component = componentRepository.findById(sanitizedOrderDTO.getComponentId())
                .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + sanitizedOrderDTO.getComponentId() + " not found."));
        supplierOrder.setComponent(component);

        SupplierOrder savedOrder = supplierOrderRepository.save(supplierOrder);

        // Publish order to upcoming events
        upcomingEventProcessorService.processUpcomingEvent(savedOrder);

        // Publish order to Kafka broker
        kafkaSupplierOrderService.sendSupplierOrderEvent(
                new SupplierOrderEvent(savedOrder, null, KafkaEvent.EventType.CREATE, savedOrder.getSupplierId(), Feature.SUPPLIER, "Test"));

        return savedOrder;
    }

    @Transactional
    public List<SupplierOrder> createSupplierOrdersInBulk(List<CreateSupplierOrderDTO> orderDTOs) {
        // Ensure same organizationId
        if (orderDTOs.stream().map(CreateSupplierOrderDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All orders must belong to the same organization.");
        }
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(orderDTOs.getFirst().getOrganizationId(), Feature.SUPPLIER_ORDER, orderDTOs.size())) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Supplier Orders for the current Subscription Plan.");
        }

        // Sanitize and map to entity
        List<SupplierOrder> orders = orderDTOs.stream()
                .map(orderDTO -> {
                    CreateSupplierOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateSupplierOrderDTO(orderDTO);
                    SupplierOrder supplierOrder = SupplierDTOMapper.mapCreateDtoToSupplierOrder(sanitizedOrderDTO);
                    Component component = componentRepository.findById(sanitizedOrderDTO.getComponentId())
                            .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + sanitizedOrderDTO.getComponentId() + " not found."));
                    supplierOrder.setComponent(component);
                    return supplierOrder;
                })
                .toList();

        List<SupplierOrder> savedOrders = supplierOrderRepository.saveAll(orders);

        // Publish order to upcoming events
        for (SupplierOrder order: savedOrders) {
            upcomingEventProcessorService.processUpcomingEvent(order);
        }

        // Publish order events to Kafka broker
        List<SupplierOrderEvent> orderEvents = new ArrayList<>();
        orders.stream()
                .map(order -> new SupplierOrderEvent(order, null, KafkaEvent.EventType.CREATE, order.getSupplierId(), Feature.SUPPLIER, "Test"))
                .forEach(orderEvents::add);

        kafkaSupplierOrderService.sendSupplierOrderEventsInBulk(orderEvents);

        return savedOrders;
    }

    @Transactional
    public List<SupplierOrder> updateSuppliersOrdersInBulk(List<UpdateSupplierOrderDTO> orderDTOs) {
        List<SupplierOrder> orders = supplierOrderRepository.findByIds(orderDTOs.stream().map(UpdateSupplierOrderDTO::getId).toList())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier Orders not found."));

        // Save old orders for event publishing
        Map<Integer, SupplierOrder> oldOrders = new HashMap<>();
        for (SupplierOrder order: orders) {
            oldOrders.put(order.getId(), order.deepCopy());
        }

        // Update orders
        List<SupplierOrder> updatedOrders = orders.stream()
                .map(order -> {
                    UpdateSupplierOrderDTO orderDTO = orderDTOs.stream()
                            .filter(dto -> dto.getId().equals(order.getId()))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Supplier Order with ID: " + order.getId() + " not found."));
                    UpdateSupplierOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeUpdateSupplierOrderDTO(orderDTO);
                    SupplierDTOMapper.setUpdateSupplierOrderDTOToUpdateOrder(order, sanitizedOrderDTO);
                    Component component = componentRepository.findById(sanitizedOrderDTO.getComponentId())
                            .orElseThrow(() -> new ResourceNotFoundException("Component with ID: " + sanitizedOrderDTO.getComponentId() + " not found."));
                    order.setComponent(component);
                    return order;
                }).toList();

        // Save
        List<SupplierOrder> savedOrders = supplierOrderRepository.saveAll(updatedOrders);

        // Publish order events to Kafka broker
        List<SupplierOrderEvent> orderEvents = new ArrayList<>();
        savedOrders.stream()
                .map(order -> {
                    SupplierOrder oldOrder = oldOrders.get(order.getId());
                    return new SupplierOrderEvent(order, oldOrder, KafkaEvent.EventType.UPDATE, order.getSupplierId(), Feature.SUPPLIER, "Test");
                })
                .forEach(orderEvents::add);

        kafkaSupplierOrderService.sendSupplierOrderEventsInBulk(orderEvents);

        return savedOrders;
    }

    @Transactional
    public List<Integer> deleteSupplierOrdersInBulk(List<Integer> orderIds) {
        List<SupplierOrder> orders = supplierOrderRepository.findAllById(orderIds);
        // Ensure same organizationId
        if (orders.stream().map(SupplierOrder::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All orders must belong to the same organization.");
        }

        supplierOrderRepository.deleteAll(orders);

        // Publish order events to Kafka broker
        List<SupplierOrderEvent> orderEvents = new ArrayList<>();
        orders.stream()
                .map(order -> new SupplierOrderEvent(null, order, KafkaEvent.EventType.CREATE, order.getSupplierId(), Feature.SUPPLIER, "Test"))
                .forEach(orderEvents::add);

        kafkaSupplierOrderService.sendSupplierOrderEventsInBulk(orderEvents);

        return orderIds;
    }

}

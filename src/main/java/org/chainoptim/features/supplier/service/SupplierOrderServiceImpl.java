package org.chainoptim.features.supplier.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.dto.SupplierDTOMapper;
import org.chainoptim.features.supplier.dto.UpdateSupplierOrderDTO;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final KafkaSupplierOrderService kafkaSupplierOrderService;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public SupplierOrderServiceImpl(
            SupplierOrderRepository supplierOrderRepository,
            KafkaSupplierOrderService kafkaSupplierOrderService,
            SubscriptionPlanLimiterService planLimiterService,
            EntitySanitizerService entitySanitizerService
    ) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.kafkaSupplierOrderService = kafkaSupplierOrderService;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<SupplierOrder> getSupplierOrdersByOrganizationId(Integer organizationId) {
        return supplierOrderRepository.findByOrganizationId(organizationId);
    }

    public List<SupplierOrder> getSupplierOrdersBySupplierId(Integer supplierId) {
        return supplierOrderRepository.findBySupplierId(supplierId);
    }

    public PaginatedResults<SupplierOrder> getSupplierOrdersBySupplierIdAdvanced(Integer supplierId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        return supplierOrderRepository.findBySupplierIdAdvanced(supplierId, searchQuery, sortBy, ascending, page, itemsPerPage);
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

        SupplierOrder savedOrder = supplierOrderRepository.save(supplierOrder);

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
                    return SupplierDTOMapper.mapCreateDtoToSupplierOrder(sanitizedOrderDTO);
                })
                .toList();

        List<SupplierOrder> savedOrders = supplierOrderRepository.saveAll(orders);

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

        List<SupplierOrder> oldOrders = orders.stream().map(SupplierOrder::deepCopy).toList();

        List<SupplierOrder> updatedOrders = orders.stream()
                .map(order -> {
                    UpdateSupplierOrderDTO orderDTO = orderDTOs.stream()
                            .filter(dto -> dto.getId().equals(order.getId()))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Supplier Order with ID: " + order.getId() + " not found."));

//                    UpdateSupplierOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeUpdateSupplierOrderDTO(orderDTO);
                    SupplierDTOMapper.setUpdateSupplierOrderDTOToUpdateOrder(order, orderDTO);

                    return order;
                }).toList();

        // Publish order events to Kafka broker
        List<SupplierOrderEvent> orderEvents = new ArrayList<>();
        orders.stream()
                .map(order -> {
                    SupplierOrder oldOrder = oldOrders.stream()
                            .filter(o -> o.getId().equals(order.getId()))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Supplier Order with ID: " + order.getId() + " not found."));
                    return new SupplierOrderEvent(order, oldOrder, KafkaEvent.EventType.UPDATE, order.getSupplierId(), Feature.SUPPLIER, "Test");
                })
                .forEach(orderEvents::add);

        kafkaSupplierOrderService.sendSupplierOrderEventsInBulk(orderEvents);

        // Save
        return supplierOrderRepository.saveAll(updatedOrders);
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

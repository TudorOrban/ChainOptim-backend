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
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
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

    public PaginatedResults<SupplierOrder> getSuppliersBySupplierIdAdvanced(Integer supplierId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        return supplierOrderRepository.findBySupplierIdAdvanced(supplierId, searchQuery, sortBy, ascending, page, itemsPerPage);
    }

    // Create
    public SupplierOrder createSupplierOrder(CreateSupplierOrderDTO orderDTO) {
        // Check if plan limit is reached
//        if (planLimiterService.isLimitReached(orderDTO.getOrganizationId(), "Supplier Orders", 1)) {
//            throw new PlanLimitReachedException("You have reached the limit of allowed Supplier Orders for the current Subscription Plan.");
//        }

        // Sanitize input and map to entity
        CreateSupplierOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateSupplierOrderDTO(orderDTO);
        SupplierOrder supplierOrder = SupplierDTOMapper.mapCreateDtoToSupplierOrder(sanitizedOrderDTO);

        SupplierOrder savedOrder = supplierOrderRepository.save(supplierOrder);

        // Publish order to Kafka broker
        kafkaSupplierOrderService.sendSupplierOrderEvent(savedOrder, KafkaEvent.EventType.CREATE);

        return savedOrder;
    }

    @Transactional
    public List<SupplierOrder> createSupplierOrdersInBulk(List<CreateSupplierOrderDTO> orderDTOs) {
        // Ensure same organizationId
        if (orderDTOs.stream().map(CreateSupplierOrderDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All orders must belong to the same organization.");
        }
        // Check if plan limit is reached
//        if (planLimiterService.isLimitReached(orderDTOs.getFirst().getOrganizationId(), "Supplier Orders", orderDTOs.size())) {
//            throw new PlanLimitReachedException("You have reached the limit of allowed Supplier Orders for the current Subscription Plan.");
//        }

        // Sanitize and map to entity
        List<SupplierOrder> orders = orderDTOs.stream()
                .map(orderDTO -> {
                    CreateSupplierOrderDTO sanitizedOrderDTO = entitySanitizerService.sanitizeCreateSupplierOrderDTO(orderDTO);
                    return SupplierDTOMapper.mapCreateDtoToSupplierOrder(sanitizedOrderDTO);
                })
                .toList();

        List<SupplierOrder> savedOrders = supplierOrderRepository.saveAll(orders);

        // Publish order events to Kafka broker
        kafkaSupplierOrderService.sendSupplierOrderEventsInBulk(savedOrders, KafkaEvent.EventType.CREATE);

        return savedOrders;
    }

    @Transactional
    public List<SupplierOrder> updateSuppliersOrdersInBulk(List<UpdateSupplierOrderDTO> orderDTOs) {
        List<SupplierOrder> orders = new ArrayList<>();
        for (UpdateSupplierOrderDTO orderDTO : orderDTOs) {
            SupplierOrder order = supplierOrderRepository.findById(orderDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier Order with ID: " + orderDTO.getId() + " not found."));

            SupplierDTOMapper.setUpdateSupplierOrderDTOToClientOrder(order, orderDTO);
            orders.add(order);
        }

        return supplierOrderRepository.saveAll(orders);
    }

    @Transactional
    public List<Integer> deleteSupplierOrdersInBulk(List<Integer> orderIds) {
        List<SupplierOrder> orders = supplierOrderRepository.findAllById(orderIds);
        supplierOrderRepository.deleteAll(orders);

        kafkaSupplierOrderService.sendSupplierOrderEventsInBulk(orders, KafkaEvent.EventType.DELETE);

        return orderIds;
    }













}

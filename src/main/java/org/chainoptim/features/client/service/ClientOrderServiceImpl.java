package org.chainoptim.features.client.service;

import org.chainoptim.core.notifications.model.KafkaEvent;
import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.client.dto.ClientDTOMapper;
import org.chainoptim.features.client.dto.CreateClientOrderDTO;
import org.chainoptim.features.client.dto.UpdateClientOrderDTO;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.client.repository.ClientOrderRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientOrderServiceImpl implements ClientOrderService {

    private final ClientOrderRepository clientOrderRepository;
    private final KafkaClientOrderService kafkaClientOrderService;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ClientOrderServiceImpl(
            ClientOrderRepository clientOrderRepository,
            KafkaClientOrderService kafkaClientOrderService,
            SubscriptionPlanLimiterService planLimiterService,
            EntitySanitizerService entitySanitizerService
    ) {
        this.clientOrderRepository = clientOrderRepository;
        this.kafkaClientOrderService = kafkaClientOrderService;
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

    public PaginatedResults<ClientOrder> getClientOrdersByClientIdAdvanced(Integer clientId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        return clientOrderRepository.findByClientIdAdvanced(clientId, searchQuery, sortBy, ascending, page, itemsPerPage);
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

        ClientOrder savedOrder = clientOrderRepository.save(clientOrder);

        // Publish order to Kafka broker
        kafkaClientOrderService.sendClientOrderEvent(savedOrder, KafkaEvent.EventType.CREATE);

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
                    return ClientDTOMapper.mapCreateDtoToClientOrder(sanitizedOrderDTO);
                })
                .toList();

        List<ClientOrder> savedOrders = clientOrderRepository.saveAll(orders);

        // Publish orders to Kafka broker
        kafkaClientOrderService.sendClientOrderEventsInBulk(savedOrders, KafkaEvent.EventType.CREATE);

        return savedOrders;
    }

    @Transactional
    public List<ClientOrder> updateClientOrdersInBulk(List<UpdateClientOrderDTO> orderDTOs) {
        List<ClientOrder> orders = new ArrayList<>();
        for (UpdateClientOrderDTO orderDTO : orderDTOs) {
            ClientOrder order = clientOrderRepository.findById(orderDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client Order with ID: " + orderDTO.getId() + " not found."));

            ClientDTOMapper.setUpdateClientOrderDTOToClientOrder(order, orderDTO);
            orders.add(order);
        }

        return clientOrderRepository.saveAll(orders);
    }


}

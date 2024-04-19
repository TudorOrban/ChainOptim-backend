package org.chainoptim.features.factory.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.factory.dto.*;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.repository.FactoryInventoryRepository;
import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FactoryInventoryServiceImpl implements FactoryInventoryService {

    private final FactoryInventoryRepository factoryInventoryRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public FactoryInventoryServiceImpl(FactoryInventoryRepository factoryInventoryRepository,
                                       SubscriptionPlanLimiterService planLimiterService,
                                       EntitySanitizerService entitySanitizerService) {
        this.factoryInventoryRepository = factoryInventoryRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public List<FactoryInventoryItem> getFactoryInventoryItemsByFactoryId(Integer factoryId) {
        return factoryInventoryRepository.findByFactoryId(factoryId);
    }

    public PaginatedResults<FactoryInventoryItem> getFactoryInventoryItemsByFactoryIdAdvanced(
            Integer factoryId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage) {
        return factoryInventoryRepository.findFactoryItemsById(factoryId, searchQuery, sortBy, ascending, page, itemsPerPage);
    }

    public FactoryInventoryItem getFactoryInventoryItemById(Integer itemId) {
        return factoryInventoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Factory inventory item with ID: " + itemId + " not found."));
    }

    // Create
    public FactoryInventoryItem createFactoryInventoryItem(CreateFactoryInventoryItemDTO itemDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(itemDTO.getOrganizationId(), Feature.FACTORY_INVENTORY, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Factory Inventory Items for the current Subscription Plan.");
        }

        // Sanitize input and map to entity
        CreateFactoryInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeCreateFactoryInventoryItemDTO(itemDTO);
        FactoryInventoryItem item = FactoryDTOMapper.convertCreateFactoryItemDTOToFactoryItem(sanitizedItemDTO);

        return factoryInventoryRepository.save(item);
    }

    @Transactional
    public List<FactoryInventoryItem> createFactoryInventoryItemsInBulk(List<CreateFactoryInventoryItemDTO> itemDTOs) {
        // Ensure same organizationId
        if (itemDTOs.stream().map(CreateFactoryInventoryItemDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All items must belong to the same organization.");
        }
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(itemDTOs.getFirst().getOrganizationId(), Feature.FACTORY_INVENTORY, itemDTOs.size())) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Factory Inventory Items for the current Subscription Plan.");
        }

        // Sanitize and map to entity
        List<FactoryInventoryItem> items = itemDTOs.stream()
                .map(itemDTO -> {
                    CreateFactoryInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeCreateFactoryInventoryItemDTO(itemDTO);
                    return FactoryDTOMapper.convertCreateFactoryItemDTOToFactoryItem(sanitizedItemDTO);
                })
                .toList();

        return factoryInventoryRepository.saveAll(items);
    }

    // Update
    public FactoryInventoryItem updateFactoryInventoryItem(UpdateFactoryInventoryItemDTO itemDTO) {
        UpdateFactoryInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeUpdateFactoryInventoryItemDTO(itemDTO);
        FactoryInventoryItem item = factoryInventoryRepository.findById(sanitizedItemDTO.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Factory inventory item with ID: " + sanitizedItemDTO.getId() + " not found."));

        item.setQuantity(sanitizedItemDTO.getQuantity());

        factoryInventoryRepository.save(item);
        return item;
    }

    @Transactional
    public List<FactoryInventoryItem> updateFactoryInventoryItemsInBulk(List<UpdateFactoryInventoryItemDTO> itemDTOs) {
        List<FactoryInventoryItem> items = new ArrayList<>();
        for (UpdateFactoryInventoryItemDTO itemDTO : itemDTOs) {
            FactoryInventoryItem item = factoryInventoryRepository.findById(itemDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Factory inventory item with ID: " + itemDTO.getId() + " not found."));

            item.setQuantity(itemDTO.getQuantity());
            items.add(item);
        }

        return factoryInventoryRepository.saveAll(items);
    }

    // Delete
    public void deleteFactoryInventoryItem(Integer itemId) {
        FactoryInventoryItem item = new FactoryInventoryItem();
        item.setId(itemId);
        factoryInventoryRepository.delete(item);
    }
}

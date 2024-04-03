package org.chainoptim.features.warehouse.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.warehouse.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.dto.WarehouseDTOMapper;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.features.warehouse.repository.WarehouseInventoryRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseInventoryServiceImpl implements WarehouseInventoryService {

    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public WarehouseInventoryServiceImpl(WarehouseInventoryRepository warehouseInventoryRepository,
                                         SubscriptionPlanLimiterService planLimiterService,
                                         EntitySanitizerService entitySanitizerService) {
        this.warehouseInventoryRepository = warehouseInventoryRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public List<WarehouseInventoryItem> getWarehouseInventoryItemsByWarehouseId(Integer warehouseId) {
        return warehouseInventoryRepository.findByWarehouseId(warehouseId);
    }

    public PaginatedResults<WarehouseInventoryItem> getWarehouseInventoryItemsByWarehouseIdAdvanced(
            Integer warehouseId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage) {
        return warehouseInventoryRepository.findWarehouseItemsById(warehouseId, searchQuery, sortBy, ascending, page, itemsPerPage);
    }

    public WarehouseInventoryItem getWarehouseInventoryItemById(Integer itemId) {
        return warehouseInventoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse inventory item with ID: " + itemId + " not found."));
    }

    // Create
    public WarehouseInventoryItem createWarehouseInventoryItem(CreateWarehouseInventoryItemDTO itemDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(itemDTO.getOrganizationId(), "Warehouse Inventory Items", 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Warehouse Inventory Items for the current Subscription Plan.");
        }

        // Sanitize input and map to entity
        CreateWarehouseInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeCreateWarehouseInventoryItemDTO(itemDTO);
        WarehouseInventoryItem item = WarehouseDTOMapper.convertCreateWarehouseItemDTOToWarehouseItem(sanitizedItemDTO);

        return warehouseInventoryRepository.save(item);
    }

    @Transactional
    public List<WarehouseInventoryItem> createWarehouseInventoryItemsInBulk(List<CreateWarehouseInventoryItemDTO> itemDTOs) {
        // Ensure same organizationId
        if (itemDTOs.stream().map(CreateWarehouseInventoryItemDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All items must belong to the same organization.");
        }
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(itemDTOs.getFirst().getOrganizationId(), "Warehouse Inventory Items", itemDTOs.size())) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Warehouse Inventory Items for the current Subscription Plan.");
        }

        // Sanitize and map to entity
        List<WarehouseInventoryItem> items = itemDTOs.stream()
                .map(itemDTO -> {
                    CreateWarehouseInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeCreateWarehouseInventoryItemDTO(itemDTO);
                    return WarehouseDTOMapper.convertCreateWarehouseItemDTOToWarehouseItem(sanitizedItemDTO);
                })
                .toList();

        return warehouseInventoryRepository.saveAll(items);
    }

    // Update
    public WarehouseInventoryItem updateWarehouseInventoryItem(UpdateWarehouseInventoryItemDTO itemDTO) {
        UpdateWarehouseInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeUpdateWarehouseInventoryItemDTO(itemDTO);
        WarehouseInventoryItem item = warehouseInventoryRepository.findById(sanitizedItemDTO.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Warehouse inventory item with ID: " + sanitizedItemDTO.getId() + " not found."));

        item.setQuantity(sanitizedItemDTO.getQuantity());

        warehouseInventoryRepository.save(item);
        return item;
    }

    @Transactional
    public List<WarehouseInventoryItem> updateWarehouseInventoryItemsInBulk(List<UpdateWarehouseInventoryItemDTO> itemDTOs) {
        List<WarehouseInventoryItem> items = new ArrayList<>();
        for (UpdateWarehouseInventoryItemDTO itemDTO : itemDTOs) {
            WarehouseInventoryItem item = warehouseInventoryRepository.findById(itemDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Warehouse inventory item with ID: " + itemDTO.getId() + " not found."));

            item.setQuantity(itemDTO.getQuantity());
            items.add(item);
        }

        return warehouseInventoryRepository.saveAll(items);
    }

    // Delete
    public void deleteWarehouseInventoryItem(Integer itemId) {
        WarehouseInventoryItem item = new WarehouseInventoryItem();
        item.setId(itemId);
        warehouseInventoryRepository.delete(item);
    }
}

package org.chainoptim.features.storage.inventory.service;

import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.storage.inventory.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.storage.warehouse.dto.WarehouseDTOMapper;
import org.chainoptim.features.storage.inventory.dto.UpdateWarehouseInventoryItemDTO;
import org.chainoptim.features.storage.inventory.model.WarehouseInventoryItem;
import org.chainoptim.features.storage.inventory.repository.WarehouseInventoryItemRepository;
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
import java.util.List;
import java.util.Map;

@Service
public class WarehouseInventoryServiceImpl implements WarehouseInventoryService {

    private final WarehouseInventoryItemRepository warehouseInventoryRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public WarehouseInventoryServiceImpl(WarehouseInventoryItemRepository warehouseInventoryRepository,
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

    public PaginatedResults<WarehouseInventoryItem> getWarehouseInventoryItemsAdvanced(SearchMode searchMode, Integer entityId, SearchParams searchParams) {
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

        return warehouseInventoryRepository.findByWarehouseIdAdvanced(searchMode, entityId, searchParams);
    }

    public WarehouseInventoryItem getWarehouseInventoryItemById(Integer itemId) {
        return warehouseInventoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse inventory item with ID: " + itemId + " not found."));
    }

    // Create
    public WarehouseInventoryItem createWarehouseInventoryItem(CreateWarehouseInventoryItemDTO itemDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(itemDTO.getOrganizationId(), Feature.WAREHOUSE_INVENTORY, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Warehouse Inventory Items for the current Subscription Plan.");
        }

        // Sanitize input and map to entity
        CreateWarehouseInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeCreateWarehouseInventoryItemDTO(itemDTO);
        WarehouseInventoryItem item = WarehouseDTOMapper.mapCreateWarehouseItemDTOToWarehouseItem(sanitizedItemDTO);

        return warehouseInventoryRepository.save(item);
    }

    @Transactional
    public List<WarehouseInventoryItem> createWarehouseInventoryItemsInBulk(List<CreateWarehouseInventoryItemDTO> itemDTOs) {
        // Ensure same organizationId
        if (itemDTOs.stream().map(CreateWarehouseInventoryItemDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All items must belong to the same organization.");
        }
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(itemDTOs.getFirst().getOrganizationId(), Feature.WAREHOUSE_INVENTORY, itemDTOs.size())) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Warehouse Inventory Items for the current Subscription Plan.");
        }

        // Sanitize and map to entity
        List<WarehouseInventoryItem> items = itemDTOs.stream()
                .map(itemDTO -> {
                    CreateWarehouseInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeCreateWarehouseInventoryItemDTO(itemDTO);
                    return WarehouseDTOMapper.mapCreateWarehouseItemDTOToWarehouseItem(sanitizedItemDTO);
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
        item.setMinimumRequiredQuantity(sanitizedItemDTO.getMinimumRequiredQuantity());

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

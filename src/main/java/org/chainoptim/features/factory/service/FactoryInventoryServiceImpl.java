package org.chainoptim.features.factory.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.factory.dto.*;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.repository.FactoryInventoryRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactoryInventoryServiceImpl implements FactoryInventoryService {

    private final FactoryInventoryRepository factoryInventoryRepository;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public FactoryInventoryServiceImpl(FactoryInventoryRepository factoryInventoryRepository, EntitySanitizerService entitySanitizerService) {
        this.factoryInventoryRepository = factoryInventoryRepository;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public List<FactoryInventoryItem> getFactoryInventoryItemsByFactoryId(Integer factoryId) {
        return factoryInventoryRepository.findByFactoryId(factoryId);
    }

    public PaginatedResults<FactoryInventoryItem> getFactoryInventoryItemsByFactoryId(
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
        CreateFactoryInventoryItemDTO sanitizedItemDTO = entitySanitizerService.sanitizeCreateFactoryInventoryItemDTO(itemDTO);
        return factoryInventoryRepository.save(FactoryDTOMapper.convertCreateFactoryItemDTOToFactoryItem(sanitizedItemDTO));
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

    // Delete
    public void deleteFactoryInventoryItem(Long itemId) {
        FactoryInventoryItem item = new FactoryInventoryItem();
        item.setId(itemId);
        factoryInventoryRepository.delete(item);
    }
}

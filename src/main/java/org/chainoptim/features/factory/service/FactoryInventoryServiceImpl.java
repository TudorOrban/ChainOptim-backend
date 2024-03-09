package org.chainoptim.features.factory.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.factory.dto.*;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.repository.FactoryInventoryRepository;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactoryInventoryServiceImpl implements FactoryInventoryService {

    private final FactoryInventoryRepository factoryInventoryRepository;

    @Autowired
    public FactoryInventoryServiceImpl(FactoryInventoryRepository factoryInventoryRepository) {
        this.factoryInventoryRepository = factoryInventoryRepository;
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

    public Optional<FactoryInventoryItem> getFactoryInventoryItemById(Integer itemId) {
        return factoryInventoryRepository.findById(itemId);
    }

    // Create
    public FactoryInventoryItem createFactoryInventoryItem(CreateFactoryInventoryItemDTO itemDTO) {
        return factoryInventoryRepository.save(FactoryDTOMapper.convertCreateFactoryItemDTOToFactoryItem(itemDTO));
    }

    // Update
    public FactoryInventoryItem updateFactoryInventoryItem(UpdateFactoryInventoryItemDTO itemDTO) {
        Optional<FactoryInventoryItem> itemOptional = factoryInventoryRepository.findById(itemDTO.getId());
        if (itemOptional.isEmpty()) {
            throw new ResourceNotFoundException("The requested inventory item does not exist");
        }
        FactoryInventoryItem item = itemOptional.get();
        item.setQuantity(itemDTO.getQuantity());

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

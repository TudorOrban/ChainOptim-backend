package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.dto.*;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;
import java.util.Optional;

public interface FactoryInventoryService {
    // Fetch
    List<FactoryInventoryItem> getFactoryInventoryItemsByFactoryId(Integer factoryId);
    PaginatedResults<FactoryInventoryItem> getFactoryInventoryItemsByFactoryId(
            Integer factoryId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage);
    FactoryInventoryItem getFactoryInventoryItemById(Integer inventoryItemId);

    // Create
    FactoryInventoryItem createFactoryInventoryItem(CreateFactoryInventoryItemDTO itemDTO);

    // Update
    FactoryInventoryItem updateFactoryInventoryItem(UpdateFactoryInventoryItemDTO itemDTO);

    // Delete
    void deleteFactoryInventoryItem(Integer itemId);
}

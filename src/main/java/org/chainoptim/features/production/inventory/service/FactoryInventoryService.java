package org.chainoptim.features.production.inventory.service;

import org.chainoptim.features.production.inventory.dto.CreateFactoryInventoryItemDTO;
import org.chainoptim.features.production.inventory.dto.UpdateFactoryInventoryItemDTO;
import org.chainoptim.features.production.inventory.model.FactoryInventoryItem;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface FactoryInventoryService {
    // Fetch
    List<FactoryInventoryItem> getFactoryInventoryItemsByFactoryId(Integer factoryId);
    PaginatedResults<FactoryInventoryItem> getFactoryInventoryItemsAdvanced(SearchMode searchMode, Integer entityId, SearchParams searchParams);

    // Create
    FactoryInventoryItem createFactoryInventoryItem(CreateFactoryInventoryItemDTO itemDTO);
    List<FactoryInventoryItem> createFactoryInventoryItemsInBulk(List<CreateFactoryInventoryItemDTO> itemDTOs);

    // Update
    List<FactoryInventoryItem> updateFactoryInventoryItemsInBulk(List<UpdateFactoryInventoryItemDTO> itemDTOs);

    // Delete
    List<Integer> deleteFactoryInventoryItemsInBulk(List<Integer> itemIds);
}

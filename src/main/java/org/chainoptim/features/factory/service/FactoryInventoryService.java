package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.dto.*;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;
import java.util.Optional;

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

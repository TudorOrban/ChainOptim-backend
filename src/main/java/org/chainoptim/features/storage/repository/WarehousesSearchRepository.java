package org.chainoptim.features.storage.repository;

import org.chainoptim.features.storage.model.Warehouse;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface WarehousesSearchRepository {
    PaginatedResults<Warehouse> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
}

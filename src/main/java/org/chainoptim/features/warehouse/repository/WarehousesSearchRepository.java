package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface WarehousesSearchRepository {
    PaginatedResults<Warehouse> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
}

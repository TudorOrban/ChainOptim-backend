package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.Warehouse;

import java.util.List;

public interface WarehousesSearchRepository {
    List<Warehouse> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending);
}

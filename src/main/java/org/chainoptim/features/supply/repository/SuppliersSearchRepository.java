package org.chainoptim.features.supply.repository;

import org.chainoptim.features.supply.model.Supplier;

import java.util.List;

public interface SuppliersSearchRepository {
    List<Supplier> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending);
}

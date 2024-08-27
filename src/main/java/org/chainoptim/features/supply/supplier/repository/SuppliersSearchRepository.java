package org.chainoptim.features.supply.supplier.repository;

import org.chainoptim.features.supply.supplier.model.Supplier;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface SuppliersSearchRepository {
    PaginatedResults<Supplier> findByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

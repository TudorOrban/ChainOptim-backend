package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface SupplierOrdersSearchRepository {
    PaginatedResults<SupplierOrder> findBySupplierIdAdvanced(
            Integer supplierId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

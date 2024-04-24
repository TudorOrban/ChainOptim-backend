package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.Map;

public interface SupplierOrdersSearchRepository {
    PaginatedResults<SupplierOrder> findBySupplierIdAdvanced(
            Integer supplierId,
            String searchQuery, Map<String, String> filters,
            String sortBy, boolean ascending,
            int page, int itemsPerPage
    );
}

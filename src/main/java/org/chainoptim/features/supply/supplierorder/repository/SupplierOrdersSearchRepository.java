package org.chainoptim.features.supply.supplierorder.repository;

import org.chainoptim.features.supply.supplierorder.model.SupplierOrder;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface SupplierOrdersSearchRepository {
    PaginatedResults<SupplierOrder> findBySupplierIdAdvanced(
            SearchMode searchMode,
            Integer supplierId,
            SearchParams searchParams
    );
}

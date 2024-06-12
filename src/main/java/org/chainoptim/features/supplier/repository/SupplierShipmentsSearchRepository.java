package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface SupplierShipmentsSearchRepository {
    PaginatedResults<SupplierShipment> findBySupplierIdAdvanced(
            SearchMode searchMode,
            Integer clientId,
            SearchParams searchParams
    );
}

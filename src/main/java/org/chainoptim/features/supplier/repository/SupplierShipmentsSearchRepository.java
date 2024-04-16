package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface SupplierShipmentsSearchRepository {
    PaginatedResults<SupplierShipment> findBySupplierOrderIdAdvanced(
            Integer supplierOrderId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

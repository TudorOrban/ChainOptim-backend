package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface ClientOrdersSearchRepository {
    PaginatedResults<ClientOrder> findByClientIdAdvanced(
            Integer supplierId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.Map;

public interface ClientOrdersSearchRepository {

    PaginatedResults<ClientOrder> findByClientIdAdvanced(
            Integer clientId,
            String searchQuery, Map<String, String> filters,
            String sortBy, boolean ascending,
            int page, int itemsPerPage
    );
}

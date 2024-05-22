package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.Map;

public interface ClientOrdersSearchRepository {

    PaginatedResults<ClientOrder> findByClientIdAdvanced(
            SearchMode searchMode,
            Integer clientId,
            SearchParams searchParams
    );
}

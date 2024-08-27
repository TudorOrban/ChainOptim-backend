package org.chainoptim.features.demand.repository;

import org.chainoptim.features.demand.model.ClientOrder;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface ClientOrdersSearchRepository {

    PaginatedResults<ClientOrder> findByClientIdAdvanced(
            SearchMode searchMode,
            Integer clientId,
            SearchParams searchParams
    );
}

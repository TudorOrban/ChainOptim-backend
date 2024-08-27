package org.chainoptim.features.demand.clientorder.repository;

import org.chainoptim.features.demand.clientorder.model.ClientOrder;
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

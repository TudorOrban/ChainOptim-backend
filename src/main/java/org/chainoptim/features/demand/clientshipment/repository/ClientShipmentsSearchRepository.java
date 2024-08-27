package org.chainoptim.features.demand.clientshipment.repository;

import org.chainoptim.features.demand.clientshipment.model.ClientShipment;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface ClientShipmentsSearchRepository {
    PaginatedResults<ClientShipment> findByClientIdAdvanced(
            SearchMode searchMode,
            Integer clientId,
            SearchParams searchParams
    );
}

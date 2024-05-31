package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientShipment;
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

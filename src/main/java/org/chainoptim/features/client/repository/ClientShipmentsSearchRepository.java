package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface ClientShipmentsSearchRepository {
    PaginatedResults<ClientShipment> findByClientOrderIdAdvanced(
            Integer clientOrderId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

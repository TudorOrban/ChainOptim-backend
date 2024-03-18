package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.Client;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface ClientsSearchRepository {
    PaginatedResults<Client> findByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

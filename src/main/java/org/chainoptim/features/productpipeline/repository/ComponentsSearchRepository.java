package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface ComponentsSearchRepository {

    PaginatedResults<Component> findByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

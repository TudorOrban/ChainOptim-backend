package org.chainoptim.features.goods.component.repository;

import org.chainoptim.features.goods.component.model.Component;
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

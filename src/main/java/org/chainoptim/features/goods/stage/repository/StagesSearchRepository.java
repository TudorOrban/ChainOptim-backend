package org.chainoptim.features.goods.stage.repository;

import org.chainoptim.features.goods.stage.model.Stage;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface StagesSearchRepository {

    PaginatedResults<Stage> findByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
}

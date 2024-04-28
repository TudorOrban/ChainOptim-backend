package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.model.Stage;
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

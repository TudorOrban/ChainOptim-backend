package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface ProductsSearchRepository {

    PaginatedResults<Product> findByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );

}

package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface ProductsSearchRepository {

    PaginatedResults<Product> findByOrganizationIdAdvanced(
            Integer organizationId,
            SearchParams searchParams
    );

}

package org.chainoptim.features.goods.product.repository;

import org.chainoptim.features.goods.product.model.Product;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface ProductsSearchRepository {

    PaginatedResults<Product> findByOrganizationIdAdvanced(
            Integer organizationId,
            SearchParams searchParams
    );

}

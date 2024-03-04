package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Product;
import java.util.List;

public interface ProductsSearchRepository {

    List<Product> findByOrganizationIdAdvanced(
            Integer organizationId, String searchQuery, String sortBy, boolean ascending);

}

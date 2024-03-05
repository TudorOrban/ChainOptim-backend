package org.chainoptim.features.product.service;

import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface ProductService {
    Product getProductWithStages(Integer productId);
    List<ProductsSearchDTO> getProductsByOrganizationId(Integer organizationId);
    PaginatedResults<ProductsSearchDTO> getProductsByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );
    ProductsSearchDTO convertToProductsSearchDTO(Product product);
}

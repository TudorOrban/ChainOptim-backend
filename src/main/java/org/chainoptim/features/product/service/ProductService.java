package org.chainoptim.features.product.service;

import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface ProductService {
    // Fetch
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

    // Create
    Product createProduct(CreateProductDTO productDTO);

    // Update
    Product updateProduct(UpdateProductDTO productDTO);

    // Delete
    void deleteProduct(Integer productId);
}

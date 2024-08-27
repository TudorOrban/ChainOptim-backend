package org.chainoptim.features.goods.product.service;

import org.chainoptim.features.goods.product.dto.CreateProductDTO;
import org.chainoptim.features.goods.product.dto.ProductOverviewDTO;
import org.chainoptim.features.goods.product.dto.ProductsSearchDTO;
import org.chainoptim.features.goods.product.dto.UpdateProductDTO;
import org.chainoptim.features.goods.product.model.Product;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface ProductService {
    // Fetch
    List<ProductsSearchDTO> getProductsByOrganizationIdSmall(Integer organizationId);
    List<ProductsSearchDTO> getProductsByOrganizationId(Integer organizationId);
    PaginatedResults<ProductsSearchDTO> getProductsByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    );

    ProductOverviewDTO getProductOverview(Integer productId);
    Product getProductWithStages(Integer productId);

    // Create
    Product createProduct(CreateProductDTO productDTO);

    // Update
    Product updateProduct(UpdateProductDTO productDTO);

    // Delete
    void deleteProduct(Integer productId);
}

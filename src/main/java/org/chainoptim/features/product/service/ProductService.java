package org.chainoptim.features.product.service;

import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductWithStages(Integer productId);
    List<ProductsSearchDTO> getProductsByOrganizationId(Integer organizationId);
    List<ProductsSearchDTO> getProductsByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending);
    ProductsSearchDTO convertToProductsSearchDTO(Product product);
}

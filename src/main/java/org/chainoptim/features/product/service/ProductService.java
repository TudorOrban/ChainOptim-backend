package org.chainoptim.features.product.service;

import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.model.Product;

import java.util.List;

public interface ProductService {
    public Product getProductWithStages(Integer productId);
    public List<ProductsSearchDTO> getProductsByOrganization(Integer organizationId);
    public ProductsSearchDTO convertToProductsSearchDTO(Product product);
}

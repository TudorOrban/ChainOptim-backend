package org.chainoptim.features.goods.product.dto;

import org.chainoptim.features.goods.product.model.Product;

public class ProductDTOMapper {

    private ProductDTOMapper() {}

    public static ProductsSearchDTO convertToProductsSearchDTO(Product product) {
        ProductsSearchDTO dto = new ProductsSearchDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        return dto;
    }

    public static Product convertCreateProductDTOToProduct(CreateProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setOrganizationId(productDTO.getOrganizationId());
        product.setUnit(productDTO.getNewUnit());

        return product;
    }

    public static Product setUpdateProductDTOToProduct(Product product, UpdateProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setUnit(productDTO.getNewUnit());

        return product;
    }
}

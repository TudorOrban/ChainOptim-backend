package org.chainoptim.features.product.dto;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.model.UnitOfMeasurement;

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
        if (productDTO.getUnitId() != null) {
            UnitOfMeasurement unit = new UnitOfMeasurement();
            unit.setId(productDTO.getUnitId());
            product.setUnit(unit);
        }

        return product;
    }
}

package org.chainoptim.features.product.dto;

import org.chainoptim.features.product.model.ProductPricing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePricingDTO {

    private Integer id;
    private Integer productId;
    private ProductPricing productPricing;
}

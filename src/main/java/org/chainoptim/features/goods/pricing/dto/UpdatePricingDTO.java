package org.chainoptim.features.goods.pricing.dto;

import org.chainoptim.features.goods.pricing.model.ProductPricing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePricingDTO {

    private Integer id;
    private Integer productId;
    private Integer organizationId;
    private ProductPricing productPricing;
}

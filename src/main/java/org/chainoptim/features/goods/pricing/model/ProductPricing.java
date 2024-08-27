package org.chainoptim.features.goods.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPricing {

    private Float pricePerUnit;
    private Map<Float, Float> pricePerVolume;
}

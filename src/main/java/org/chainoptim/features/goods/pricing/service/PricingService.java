package org.chainoptim.features.goods.pricing.service;

import org.chainoptim.features.goods.pricing.dto.CreatePricingDTO;
import org.chainoptim.features.goods.pricing.dto.UpdatePricingDTO;
import org.chainoptim.features.goods.pricing.model.Pricing;

public interface PricingService {

    Pricing getPricingByProductId(Integer productId);
    Pricing createPricing(CreatePricingDTO pricing);
    Pricing updatePricing(UpdatePricingDTO pricing);
    void deletePricing(Integer productId);
}

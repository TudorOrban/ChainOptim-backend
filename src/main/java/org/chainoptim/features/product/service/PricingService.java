package org.chainoptim.features.product.service;

import org.chainoptim.features.product.dto.CreatePricingDTO;
import org.chainoptim.features.product.dto.UpdatePricingDTO;
import org.chainoptim.features.product.model.Pricing;

public interface PricingService {

    Pricing getPricingByProductId(Integer productId);
    Pricing createPricing(CreatePricingDTO pricing);
    Pricing updatePricing(UpdatePricingDTO pricing);
    void deletePricing(Integer productId);
}

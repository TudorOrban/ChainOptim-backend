package org.chainoptim.features.product.service;

import org.chainoptim.features.product.model.Pricing;

public interface PricingService {

    Pricing getPricingByProductId(Integer productId);
    Pricing createPricing(Pricing pricing);
    Pricing updatePricing(Pricing pricing);
    void deletePricing(Integer productId);
}

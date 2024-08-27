package org.chainoptim.features.product.dto;

import org.chainoptim.features.product.model.Pricing;

public class PricingDTOMapper {

    private PricingDTOMapper() {}

    public static Pricing mapCreatePricingDTOToPricing(CreatePricingDTO createPricingDTO) {
        Pricing pricing = new Pricing();
        pricing.setProductId(createPricingDTO.getProductId());
        pricing.setOrganizationId(createPricingDTO.getOrganizationId());
        pricing.setProductPricing(createPricingDTO.getProductPricing());
        return pricing;
    }

    public static Pricing setUpdatePricingDTOToPricing(UpdatePricingDTO updatePricingDTO, Pricing pricing) {
        pricing.setProductPricing(updatePricingDTO.getProductPricing());
        return pricing;
    }
}

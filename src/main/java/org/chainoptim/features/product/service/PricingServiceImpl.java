package org.chainoptim.features.product.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.model.Pricing;
import org.chainoptim.features.product.repository.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PricingServiceImpl implements PricingService {

    private final PricingRepository pricingRepository;

    @Autowired
    public PricingServiceImpl(PricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    public Pricing getPricingByProductId(Integer productId) {
        return pricingRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing with Product ID: " + productId + " not found."));
    }

    public Pricing createPricing(Pricing pricing) {
        Optional<Pricing> existingPricing = pricingRepository.findByProductId(pricing.getProductId());
        if (existingPricing.isPresent()) {
            throw new ResourceNotFoundException("Pricing with Product ID: " + pricing.getProductId() + " already exists.");
        }
        return pricingRepository.save(pricing);
    }

    public Pricing updatePricing(Pricing pricing) {
        Pricing existingPricing = pricingRepository.findByProductId(pricing.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Pricing with Product ID: " + pricing.getProductId() + " not found."));
        pricing.setId(existingPricing.getId());
        return pricingRepository.save(pricing);
    }

    public void deletePricing(Integer productId) {
        Pricing pricing = pricingRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing with Product ID: " + productId + " not found."));
        pricingRepository.delete(pricing);
    }
}

package org.chainoptim.features.goods.pricing.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.goods.pricing.dto.CreatePricingDTO;
import org.chainoptim.features.goods.pricing.dto.PricingDTOMapper;
import org.chainoptim.features.goods.pricing.dto.UpdatePricingDTO;
import org.chainoptim.features.goods.pricing.model.Pricing;
import org.chainoptim.features.goods.pricing.repository.PricingRepository;
import org.chainoptim.shared.enums.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PricingServiceImpl implements PricingService {

    private final PricingRepository pricingRepository;
    private final SubscriptionPlanLimiterService planLimiterService;

    @Autowired
    public PricingServiceImpl(PricingRepository pricingRepository,
                              SubscriptionPlanLimiterService planLimiterService) {
        this.pricingRepository = pricingRepository;
        this.planLimiterService = planLimiterService;
    }

    public Pricing getPricingByProductId(Integer productId) {
        return pricingRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing with Product ID: " + productId + " not found."));
    }

    public Pricing createPricing(CreatePricingDTO pricingDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(pricingDTO.getOrganizationId(), Feature.PRICING, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed pricings for the current Subscription Plan.");
        }

        Optional<Pricing> existingPricing = pricingRepository.findByProductId(pricingDTO.getProductId());
        if (existingPricing.isPresent()) {
            throw new ResourceNotFoundException("Pricing with Product ID: " + pricingDTO.getProductId() + " already exists.");
        }
        Pricing pricing = PricingDTOMapper.mapCreatePricingDTOToPricing(pricingDTO);
        return pricingRepository.save(pricing);
    }

    public Pricing updatePricing(UpdatePricingDTO pricingDTO) {
        Pricing existingPricing = pricingRepository.findByProductId(pricingDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Pricing with Product ID: " + pricingDTO.getProductId() + " not found."));
        pricingDTO.setId(existingPricing.getId());
        Pricing pricing = PricingDTOMapper.setUpdatePricingDTOToPricing(pricingDTO, existingPricing);
        return pricingRepository.save(pricing);
    }

    public void deletePricing(Integer productId) {
        Pricing pricing = pricingRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing with Product ID: " + productId + " not found."));
        pricingRepository.delete(pricing);
    }
}

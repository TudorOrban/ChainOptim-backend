package org.chainoptim.features.product.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.dto.CreatePricingDTO;
import org.chainoptim.features.product.dto.PricingDTOMapper;
import org.chainoptim.features.product.dto.UpdatePricingDTO;
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

    public Pricing createPricing(CreatePricingDTO pricingDTO) {
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

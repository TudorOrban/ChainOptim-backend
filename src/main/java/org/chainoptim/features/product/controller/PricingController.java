package org.chainoptim.features.product.controller;

import org.chainoptim.features.product.model.Pricing;
import org.chainoptim.features.product.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pricings")
public class PricingController {

    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Pricing> getPricingByProductId(@PathVariable Integer productId) {
        Pricing pricing = pricingService.getPricingByProductId(productId);
        return ResponseEntity.ok(pricing);
    }

    @PostMapping("/create")
    public ResponseEntity<Pricing> createPricing(@RequestBody Pricing pricing) {
        Pricing newPricing = pricingService.createPricing(pricing);
        return ResponseEntity.ok(newPricing);
    }

    @PutMapping("/update")
    public ResponseEntity<Pricing> updatePricing(@RequestBody Pricing pricing) {
        Pricing updatedPricing = pricingService.updatePricing(pricing);
        return ResponseEntity.ok(updatedPricing);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deletePricing(@PathVariable Integer productId) {
        pricingService.deletePricing(productId);
        return ResponseEntity.noContent().build();
    }
}

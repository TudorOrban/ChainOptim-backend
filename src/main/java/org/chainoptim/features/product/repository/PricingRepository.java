package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {

    Optional<Pricing> findByProductId(Integer productId);
}

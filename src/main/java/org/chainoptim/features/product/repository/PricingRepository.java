package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {

    Optional<Pricing> findByProductId(Integer productId);

    @Query("SELECT COUNT(p) FROM Pricing p WHERE p.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

    @Query("SELECT p.organizationId FROM Pricing p WHERE p.id = :pricingId")
    Optional<Integer> findOrganizationIdById(@Param("pricingId") Long pricingId);
}

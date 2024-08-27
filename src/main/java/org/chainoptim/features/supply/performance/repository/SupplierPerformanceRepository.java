package org.chainoptim.features.supply.performance.repository;

import org.chainoptim.features.supply.performance.model.SupplierPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierPerformanceRepository extends JpaRepository<SupplierPerformance, Integer> {

    Optional<SupplierPerformance> findBySupplierId(Integer supplierId);
}

package org.chainoptim.features.production.analysis.performance.repository;

import org.chainoptim.features.production.analysis.performance.model.FactoryPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FactoryPerformanceRepository extends JpaRepository<FactoryPerformance, Integer> {

    Optional<FactoryPerformance> findByFactoryId(Integer factoryId);
}

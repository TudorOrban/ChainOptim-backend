package org.chainoptim.features.scanalysis.production.productionhistory.repository;

import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FactoryProductionHistoryRepository extends JpaRepository<FactoryProductionHistory, Integer> {


    Optional<FactoryProductionHistory> findByFactoryId(Integer factoryId);
}

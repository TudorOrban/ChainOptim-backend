package org.chainoptim.features.production.analysis.productionhistory.repository;

import org.chainoptim.features.production.analysis.productionhistory.model.FactoryProductionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FactoryProductionHistoryRepository extends JpaRepository<FactoryProductionHistory, Integer> {


    Optional<FactoryProductionHistory> findByFactoryId(Integer factoryId);

    @Query("SELECT id FROM FactoryProductionHistory WHERE factoryId = :factoryId")
    Optional<Integer> findIdByFactoryId(@Param("factoryId") Integer factoryId);

    @Query("SELECT factoryId FROM FactoryProductionHistory WHERE id = :id")
    Optional<Integer> findFactoryIdById(@Param("id") Integer id);
}

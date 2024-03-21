package org.chainoptim.features.scanalysis.production.factorygraph.repository;

import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryProductionGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactoryProductionGraphRepository extends JpaRepository<FactoryProductionGraph, Integer> {

    @Query("SELECT pg FROM FactoryProductionGraph pg " +
            "WHERE pg.factoryId = :factoryId")
    List<FactoryProductionGraph> findProductionGraphByFactoryId(@Param("factoryId") Integer factoryId);
}

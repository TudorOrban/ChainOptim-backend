package org.chainoptim.features.evaluation.production.connection.repository;

import org.chainoptim.features.evaluation.production.connection.model.FactoryStageConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactoryStageConnectionRepository extends JpaRepository<FactoryStageConnection, Integer> {

    @Query("SELECT c FROM FactoryStageConnection c " +
            "WHERE c.factoryId = :factoryId")
    List<FactoryStageConnection> findFactoryStageConnectionsByFactoryId(@Param("factoryId") Integer factoryId);
}

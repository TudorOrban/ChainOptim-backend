package org.chainoptim.features.scanalysis.production.factoryconnection.repository;

import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactoryStageConnectionRepository extends JpaRepository<FactoryStageConnection, Integer> {

    @Query("SELECT c FROM FactoryStageConnection c " +
            "WHERE c.factoryId = :factoryId")
    List<FactoryStageConnection> findFactoryStageConnectionsByFactoryId(@Param("factoryId") Integer factoryId);
}

package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.evaluation.production.connection.model.FactoryStageConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Integer>, FactoriesSearchRepository {

    List<Factory> findByOrganizationId(Integer organizationId);

    @Query("SELECT f.organizationId FROM Factory f WHERE f.id = :factoryId")
    Optional<Integer> findOrganizationIdById(@Param("factoryId") Long factoryId);

    @Query("SELECT f FROM Factory f " +
            "LEFT JOIN FETCH f.factoryStages fs " +
            "LEFT JOIN FETCH fs.stage WHERE f.id = :factoryId")
    Optional<Factory> findFactoryWithStagesById(@Param("factoryId") Integer factoryId);


    @Query("SELECT c FROM FactoryStageConnection c " +
            "WHERE c.factoryId = :factoryId")
    List<FactoryStageConnection> findFactoryStageConnectionsByFactoryId(@Param("factoryId") Integer factoryId);
}
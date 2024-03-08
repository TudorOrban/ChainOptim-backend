package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Integer>, FactoriesSearchRepository {

    List<Factory> findByOrganizationId(Integer organizationId);

    @Query("SELECT p.organizationId FROM Factory p WHERE p.id = :factoryId")
    Optional<Integer> findOrganizationIdById(@Param("factoryId") Long factoryId);

}
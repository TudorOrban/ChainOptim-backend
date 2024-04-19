package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.FactoryStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FactoryStageRepository extends JpaRepository<FactoryStage, Integer> {

    @Query("SELECT fs FROM FactoryStage fs JOIN FETCH fs.stage WHERE fs.id = :id")
    Optional<FactoryStage> findByIdWithStage(@Param("id") Integer id);

    @Query("SELECT COUNT(fs) FROM FactoryStage fs JOIN fs.factory f WHERE f.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);
}

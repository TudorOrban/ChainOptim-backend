package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.dto.FactoryStageSearchDTO;
import org.chainoptim.features.factory.model.FactoryStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FactoryStageRepository extends JpaRepository<FactoryStage, Integer> {

    @Query("SELECT new org.chainoptim.features.factory.dto.FactoryStageSearchDTO(fs.id, fs.factory.id, fs.stage.id, fs.stage.name) FROM FactoryStage fs JOIN fs.factory f WHERE f.organizationId = :organizationId")
    List<FactoryStageSearchDTO> findByOrganizationId(Integer organizationId);

    @Query("SELECT new org.chainoptim.features.factory.dto.FactoryStageSearchDTO(fs.id, fs.factory.id, fs.stage.id, fs.stage.name) FROM FactoryStage fs WHERE fs.factory.id = :factoryId")
    List<FactoryStageSearchDTO> findByFactoryId(Integer factoryId);

    @Query("SELECT fs FROM FactoryStage fs JOIN FETCH fs.stage WHERE fs.id = :id")
    Optional<FactoryStage> findByIdWithStage(@Param("id") Integer id);

    @Query("SELECT COUNT(fs) FROM FactoryStage fs JOIN fs.factory f WHERE f.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

    @Query("SELECT fs.organizationId FROM FactoryStage fs WHERE fs.id = :stageId")
    Optional<Integer> findOrganizationIdById(@Param("stageId") Long stageId);

}

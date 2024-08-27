package org.chainoptim.features.production.factory.repository;

import org.chainoptim.features.production.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.production.factory.model.Factory;
import org.chainoptim.features.production.stageconnection.model.FactoryStageConnection;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Integer>, FactoriesSearchRepository {

    List<Factory> findByOrganizationId(Integer organizationId);

    @Query("SELECT new org.chainoptim.features.production.factory.dto.FactoriesSearchDTO(f.id, f.name, f.createdAt, f.updatedAt, f.location, f.overallScore, f.resourceDistributionScore, f.resourceReadinessScore, f.resourceUtilizationScore) FROM Factory f WHERE f.organizationId = :organizationId")
    List<FactoriesSearchDTO> findByOrganizationIdSmall(Integer organizationId);

    @Query("SELECT f.organizationId FROM Factory f WHERE f.id = :factoryId")
    Optional<Integer> findOrganizationIdById(@Param("factoryId") Long factoryId);

    @Query("SELECT f FROM Factory f WHERE f.name = :name")
    Optional<Factory> findByName(@Param("name") String name);

    @Query("SELECT f FROM Factory f " +
            "LEFT JOIN FETCH f.factoryStages fs " +
            "LEFT JOIN FETCH fs.stage s " +
            "LEFT JOIN FETCH s.stageInputs si " +
            "LEFT JOIN FETCH s.stageOutputs so " +
            "WHERE f.id = :factoryId")
    Optional<Factory> findFactoryWithStagesById(@Param("factoryId") Integer factoryId);

    @Query("SELECT c FROM FactoryStageConnection c " +
            "WHERE c.factoryId = :factoryId")
    List<FactoryStageConnection> findFactoryStageConnectionsByFactoryId(@Param("factoryId") Integer factoryId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(s.id, s.name) FROM Stage s WHERE " +
            "s.id IN (SELECT fs.stage.id FROM FactoryStage fs WHERE fs.factory.id = :factoryId)")
    List<SmallEntityDTO> findFactoryStagesByFactoryId(@Param("factoryId") Integer factoryId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(c.id, c.name) FROM Component c WHERE " +
            "c.id IN (SELECT so.component.id FROM StageOutput so WHERE " +
            "so.stage.id IN (SELECT s.id FROM Stage s WHERE " +
            "s.id IN (SELECT fs.stage.id FROM FactoryStage fs WHERE fs.factory.id = :factoryId)))")
    List<SmallEntityDTO> findManufacturedComponentsByFactoryId(@Param("factoryId") Integer factoryId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(p.id, p.name) FROM Product p WHERE " +
            "p.id IN (SELECT so.productId FROM StageOutput so WHERE " +
            "so.stage.id IN (SELECT s.id FROM Stage s WHERE " +
            "s.id IN (SELECT fs.stage.id FROM FactoryStage fs WHERE fs.factory.id = :factoryId)))")
    List<SmallEntityDTO> findManufacturedProductsByFactoryId(@Param("factoryId") Integer factoryId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(s.id, s.name) FROM Supplier s WHERE " +
            "s.id IN (SELECT ss.supplierId FROM SupplierShipment ss WHERE ss.destFactoryId = :factoryId)")
    List<SmallEntityDTO> findDeliveredFromSuppliersByFactoryId(@Param("factoryId") Integer factoryId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(c.id, c.name) FROM Client c WHERE " +
            "c.id IN (SELECT cs.clientId FROM ClientShipment cs WHERE cs.srcFactoryId = :factoryId)")
    List<SmallEntityDTO> findDeliveredToClientsByFactoryId(@Param("factoryId") Integer factoryId);

    long countByOrganizationId(Integer organizationId);
}
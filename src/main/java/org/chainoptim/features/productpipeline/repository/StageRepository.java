package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.dto.StagesSearchDTO;
import org.chainoptim.features.productpipeline.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StageRepository extends JpaRepository<Stage, Integer> {

    @Query("SELECT new org.chainoptim.features.productpipeline.dto.StagesSearchDTO(s.id, s.name, s.createdAt) FROM Stage s WHERE s.organizationId = :organizationId")
    List<StagesSearchDTO> findByOrganizationIdSmall(Integer organizationId);

    @Query("SELECT s FROM Stage s " +
            "WHERE s.productId = :productId")
    List<Stage> findByProductId(@Param("productId") Integer productId);

    @Query("SELECT s.organizationId FROM Stage s WHERE s.id = :stageId")
    Optional<Integer> findOrganizationIdById(@Param("stageId") Long stageId);

    @Query("SELECT s FROM Stage s WHERE s.name = :name")
    Optional<Stage> findByName(@Param("name") String name);

    long countByOrganizationId(Integer organizationId);
}

package org.chainoptim.features.goods.stage.repository;

import org.chainoptim.features.goods.stage.dto.StagesSearchDTO;
import org.chainoptim.features.goods.stage.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StageRepository extends JpaRepository<Stage, Integer>, StagesSearchRepository {

    @Query("SELECT new org.chainoptim.features.goods.stage.dto.StagesSearchDTO(s.id, s.name, s.createdAt) FROM Stage s WHERE s.organizationId = :organizationId")
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

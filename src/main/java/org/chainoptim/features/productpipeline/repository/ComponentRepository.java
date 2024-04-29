package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.dto.ComponentsSearchDTO;
import org.chainoptim.features.productpipeline.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Integer>, ComponentsSearchRepository {
    List<Component> findByOrganizationId(Integer organizationId);

    @Query("SELECT new org.chainoptim.features.productpipeline.dto.ComponentsSearchDTO(c.id, c.name) FROM Component c WHERE c.organizationId = :organizationId")
    List<ComponentsSearchDTO> findByOrganizationIdSmall(Integer organizationId);

    @Query("SELECT c.organizationId FROM Component c WHERE c.id = :componentId")
    Optional<Integer> findOrganizationIdById(@Param("componentId") Long componentId);

    @Query("SELECT c FROM Component c WHERE c.name = :name")
    Optional<Component> findByName(@Param("name") String name);

    @Query("SELECT COUNT(c) FROM Component c WHERE c.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);
}

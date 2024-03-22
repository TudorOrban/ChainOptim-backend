package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Integer> {
    List<Component> findByOrganizationId(Integer organizationId);

    @Query("SELECT c.organizationId FROM Component c WHERE c.id = :componentId")
    Optional<Integer> findOrganizationIdById(@Param("componentId") Long componentId);
}

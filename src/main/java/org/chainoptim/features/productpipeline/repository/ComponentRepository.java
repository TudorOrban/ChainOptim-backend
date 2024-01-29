package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Integer> {
    List<Component> findByOrganizationId(Integer organizationId);
}

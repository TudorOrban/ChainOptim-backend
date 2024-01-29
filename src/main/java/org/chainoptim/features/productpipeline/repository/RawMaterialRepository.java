package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.model.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Integer> {
    List<RawMaterial> findByOrganizationId(Integer organizationId);
}

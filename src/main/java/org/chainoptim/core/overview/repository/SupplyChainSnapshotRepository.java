package org.chainoptim.core.overview.repository;

import org.chainoptim.core.overview.model.SupplyChainSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SupplyChainSnapshotRepository extends JpaRepository<SupplyChainSnapshot, Integer> {

    Optional<SupplyChainSnapshot> findByOrganizationId(Integer organizationId);

    @Query("SELECT s.id FROM SupplyChainSnapshot s WHERE s.organizationId = :organizationId")
    Optional<Integer> findIdByOrganizationId(Integer organizationId);
}

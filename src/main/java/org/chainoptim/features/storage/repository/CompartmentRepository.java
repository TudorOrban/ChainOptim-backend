package org.chainoptim.features.storage.repository;

import org.chainoptim.features.storage.model.Compartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompartmentRepository extends JpaRepository<Compartment, Integer> {

    List<Compartment> findByOrganizationId(Integer organizationId);
    List<Compartment> findByWarehouseId(Integer warehouseId);

    @Query("SELECT c.organizationId FROM Crate c WHERE c.id = :compartmentId")
    Optional<Integer> findOrganizationIdById(@Param("compartmentId") Long compartmentId);

    @Query("SELECT COUNT(c) FROM Compartment c WHERE c.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

}

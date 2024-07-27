package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.Compartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompartmentRepository extends JpaRepository<Compartment, Integer> {

    List<Compartment> findByOrganizationId(Integer organizationId);
    List<Compartment> findByWarehouseId(Integer warehouseId);

    @Query("SELECT w.organizationId FROM Warehouse w WHERE w.id = :warehouseId")
    Optional<Integer> findOrganizationIdById(@Param("warehouseId") Long warehouseId);

}

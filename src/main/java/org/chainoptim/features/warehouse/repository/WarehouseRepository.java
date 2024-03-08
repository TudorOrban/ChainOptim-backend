package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer>, WarehousesSearchRepository {

    List<Warehouse> findByOrganizationId(Integer organizationId);

    @Query("SELECT p.organizationId FROM Warehouse p WHERE p.id = :warehouseId")
    Optional<Integer> findOrganizationIdById(@Param("warehouseId") Long warehouseId);
}
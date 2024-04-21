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

    @Query("SELECT w.organizationId FROM Warehouse w WHERE w.id = :warehouseId")
    Optional<Integer> findOrganizationIdById(@Param("warehouseId") Long warehouseId);

    @Query("SELECT w FROM Warehouse w WHERE w.name = :name")
    Optional<Warehouse> findByName(@Param("name") String name);

    long countByOrganizationId(Integer organizationId);
}
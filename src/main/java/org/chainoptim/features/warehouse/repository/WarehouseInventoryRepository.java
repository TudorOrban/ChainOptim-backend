package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventoryItem, Integer> {

    List<WarehouseInventoryItem> findByOrganizationId(Integer organizationId);

    List<WarehouseInventoryItem> findByWarehouseId(Integer warehouseId);

    @Query("SELECT COUNT(ii) FROM FactoryInventoryItem ii WHERE ii.organizationId = :organizationId")
    long findCountByOrganizationId(@Param("organizationId") Integer organizationId);
}

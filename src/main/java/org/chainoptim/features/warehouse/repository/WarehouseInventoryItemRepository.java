package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseInventoryItemRepository extends JpaRepository<WarehouseInventoryItem, Integer>, WarehouseInventoryItemSearchRepository {

    List<WarehouseInventoryItem> findByOrganizationId(Integer organizationId);

    List<WarehouseInventoryItem> findByWarehouseId(Integer warehouseId);

    @Query("SELECT COUNT(ii) FROM WarehouseInventoryItem ii WHERE ii.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

    @Query("SELECT ii.organizationId FROM WarehouseInventoryItem ii WHERE ii.id = :itemId")
    Optional<Integer> findOrganizationIdById(@Param("itemId") Long itemId);
}

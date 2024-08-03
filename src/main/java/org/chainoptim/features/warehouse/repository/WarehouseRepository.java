package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
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

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(c.id, c.name) FROM Compartment c WHERE " +
            "c.warehouseId = :warehouseId")
    List<SmallEntityDTO> findCompartmentsByWarehouseId(@Param("warehouseId") Integer warehouseId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(c.id, c.name) FROM Component c WHERE " +
            "c.id IN (SELECT ii.component.id FROM WarehouseInventoryItem ii WHERE ii.warehouseId = :warehouseId)")
    List<SmallEntityDTO> findStoredComponentsByWarehouseId(@Param("warehouseId") Integer warehouseId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(p.id, p.name) FROM Product p WHERE " +
            "p.id IN (SELECT ii.product.id FROM WarehouseInventoryItem ii WHERE ii.warehouseId = :warehouseId)")
    List<SmallEntityDTO> findStoredProductsByWarehouseId(@Param("warehouseId") Integer warehouseId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(s.id, s.name) FROM Supplier s WHERE " +
            "s.id IN (SELECT ss.supplierId FROM SupplierShipment ss WHERE ss.destWarehouseId = :warehouseId)")
    List<SmallEntityDTO> findDeliveredFromSuppliersByWarehouseId(@Param("warehouseId") Integer warehouseId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(c.id, c.name) FROM Client c WHERE " +
            "c.id IN (SELECT cs.clientId FROM ClientShipment cs WHERE cs.srcWarehouseId = :warehouseId)")
    List<SmallEntityDTO> findDeliveredToClientsByWarehouseId(@Param("warehouseId") Integer warehouseId);

    long countByOrganizationId(Integer organizationId);
}
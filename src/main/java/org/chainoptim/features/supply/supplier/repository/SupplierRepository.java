package org.chainoptim.features.supply.supplier.repository;

import org.chainoptim.features.supply.supplier.model.Supplier;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>, SuppliersSearchRepository {

    List<Supplier> findByOrganizationId(Integer organizationId);

    @Query("SELECT p.organizationId FROM Supplier p WHERE p.id = :supplierId")
    Optional<Integer> findOrganizationIdById(@Param("supplierId") Long supplierId);

    @Query("SELECT p FROM Supplier p WHERE p.name = :name")
    Optional<Supplier> findByName(@Param("name") String name);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(c.id, c.name) FROM Component c WHERE " +
            "c.id IN (SELECT so.component.id FROM SupplierOrder so WHERE so.supplierId = :supplierId)")
    List<SmallEntityDTO> findSuppliedComponentsBySupplierId(@Param("supplierId") Integer supplierId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(f.id, f.name) FROM Factory f WHERE " +
            "f.id IN (SELECT ss.destFactoryId FROM SupplierShipment ss WHERE ss.supplierId = :supplierId)")
    List<SmallEntityDTO> findDeliveredToFactoriesBySupplierId(@Param("supplierId") Integer supplierId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(w.id, w.name) FROM Warehouse w WHERE " +
            "w.id IN (SELECT ss.destWarehouseId FROM SupplierShipment ss WHERE ss.supplierId = :supplierId)")
    List<SmallEntityDTO> findDeliveredToWarehousesBySupplierId(@Param("supplierId") Integer supplierId);

    long countByOrganizationId(Integer organizationId);
}
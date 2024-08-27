package org.chainoptim.features.supply.repository;

import org.chainoptim.features.supply.model.SupplierShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierShipmentRepository extends JpaRepository<SupplierShipment, Integer>, SupplierShipmentsSearchRepository {

    List<SupplierShipment>  findByOrganizationId(Integer organizationId);

    @Query("SELECT ss FROM SupplierShipment ss " +
            "WHERE ss.supplierId = :supplierId")
    List<SupplierShipment> findBySupplierId(@Param("supplierId") Integer supplierId);

    @Query("SELECT ss FROM SupplierShipment ss " +
            "WHERE ss.supplierOrderId IN :orderIds")
    List<SupplierShipment> findBySupplierOrderIds(@Param("orderIds") List<Integer> supplierOrderIds);

    @Query("SELECT COUNT(ss) FROM SupplierShipment ss, SupplierOrder so WHERE ss.supplierOrderId = so.id AND so.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

    @Query("SELECT ss.organizationId FROM SupplierShipment ss WHERE ss.id = :shipmentId")
    Optional<Integer> findOrganizationIdById(@Param("shipmentId") Long shipmentId);
}

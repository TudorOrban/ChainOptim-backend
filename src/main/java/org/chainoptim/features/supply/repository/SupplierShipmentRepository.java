package org.chainoptim.features.supply.repository;

import org.chainoptim.features.supply.model.SupplierShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierShipmentRepository extends JpaRepository<SupplierShipment, Integer> {

    @Query("SELECT ss FROM SupplierShipment ss " +
            "WHERE ss.supplierOrderId = :orderId")
    List<SupplierShipment> findBySupplyOrderId(@Param("orderId") Integer orderId);
}

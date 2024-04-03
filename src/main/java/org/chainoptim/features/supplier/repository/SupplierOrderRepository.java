package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Integer>, SupplierOrdersSearchRepository {

    List<SupplierOrder> findByOrganizationId(Integer organizationId);

    List<SupplierOrder> findBySupplierId(Integer supplierId);

    @Query("SELECT COUNT(so) FROM SupplierOrder so WHERE so.organizationId = :organizationId")
    long findCountByOrganizationId(@Param("organizationId") Integer organizationId);

}

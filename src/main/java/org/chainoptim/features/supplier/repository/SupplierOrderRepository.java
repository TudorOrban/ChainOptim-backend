package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Integer>, SupplierOrdersSearchRepository {

    List<SupplierOrder> findByOrganizationId(Integer organizationId);

    List<SupplierOrder> findBySupplierId(Integer supplierId);

    @Query("SELECT so FROM SupplierOrder so WHERE so.id IN :ids")
    Optional<List<SupplierOrder>> findByIds(@Param("ids") List<Integer> ids);

    @Query("SELECT so.organizationId FROM SupplierOrder so WHERE so.id = :supplierId")
    Optional<Integer> findOrganizationIdById(@Param("supplierId") Long supplierId);

    @Query("SELECT COUNT(so) FROM SupplierOrder so WHERE so.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

}

package org.chainoptim.features.supply.repository;

import org.chainoptim.features.supply.model.SupplierOrder;

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

    @Query("SELECT so.organizationId FROM SupplierOrder so WHERE so.id = :supplierOrderId")
    Optional<Integer> findOrganizationIdById(@Param("supplierOrderId") Long supplierOrderId);

    @Query("SELECT so FROM SupplierOrder so WHERE so.companyId = :companyId")
    Optional<SupplierOrder> findByCompanyId(@Param("companyId") String companyId);

    @Query("SELECT so FROM SupplierOrder so WHERE so.companyId IN :companyIds")
    List<SupplierOrder> findByCompanyIds(@Param("companyIds") List<String> companyIds);

    @Query("SELECT COUNT(so) FROM SupplierOrder so WHERE so.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

}

package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Integer>, SupplierOrdersSearchRepository {

    List<SupplierOrder> findByOrganizationId(Integer organizationId);

    List<SupplierOrder> findBySupplierId(Integer supplierId);

}

package org.chainoptim.features.supply.repository;

import org.chainoptim.features.supply.model.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Integer> {

    List<SupplierOrder> findByOrganizationId(Integer organizationId);
    List<SupplierOrder> findBySupplierId(Integer supplierId);

}

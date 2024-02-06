package org.chainoptim.features.supply.repository;

import org.chainoptim.features.supply.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByOrganizationId(Integer organizationId);
}
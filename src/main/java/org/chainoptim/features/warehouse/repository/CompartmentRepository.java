package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.Compartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompartmentRepository extends JpaRepository<Compartment, Integer> {

    List<Compartment> findByOrganizationId(Integer organizationId);
    List<Compartment> findByWarehouseId(Integer warehouseId);
}

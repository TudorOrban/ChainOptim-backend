package org.chainoptim.features.supply.repository;

import org.chainoptim.features.supply.model.Supplier;
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
}
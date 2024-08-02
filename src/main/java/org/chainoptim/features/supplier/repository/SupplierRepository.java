package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
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

    @Query("SELECT p FROM Supplier p WHERE p.name = :name")
    Optional<Supplier> findByName(@Param("name") String name);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(c.id, c.name) FROM Component c WHERE " +
            "c.id IN (SELECT so.component.id FROM SupplierOrder so WHERE so.supplierId = :supplierId)")
    List<SmallEntityDTO> findComponentsBySupplierId(@Param("supplierId") Integer supplierId);

    long countByOrganizationId(Integer organizationId);
}
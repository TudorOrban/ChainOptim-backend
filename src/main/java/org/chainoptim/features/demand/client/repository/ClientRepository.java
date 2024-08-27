package org.chainoptim.features.demand.client.repository;

import org.chainoptim.features.demand.client.model.Client;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer>, ClientsSearchRepository {

    List<Client> findByOrganizationId(Integer organizationId);

    @Query("SELECT c.organizationId FROM Client c WHERE c.id = :clientId")
    Optional<Integer> findOrganizationIdById(@Param("clientId") Long clientId);

    @Query("SELECT c FROM Client c WHERE c.name = :name")
    Optional<Client> findByName(@Param("name") String name);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(p.id, p.name) FROM Product p WHERE " +
            "p.id IN (SELECT co.product.id FROM ClientOrder co WHERE co.clientId = :clientId)")
    List<SmallEntityDTO> findSuppliedProductsByClientId(@Param("clientId") Integer clientId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(f.id, f.name) FROM Factory f WHERE " +
            "f.id IN (SELECT cs.srcFactoryId FROM ClientShipment cs WHERE cs.clientId = :clientId)")
    List<SmallEntityDTO> findDeliveredToFactoriesByClientId(@Param("clientId") Integer clientId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(w.id, w.name) FROM Warehouse w WHERE " +
            "w.id IN (SELECT cs.srcWarehouseId FROM ClientShipment cs WHERE cs.clientId = :clientId)")
    List<SmallEntityDTO> findDeliveredToWarehousesByClientId(@Param("clientId") Integer clientId);

    long countByOrganizationId(Integer organizationId);
}

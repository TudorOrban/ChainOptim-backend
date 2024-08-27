package org.chainoptim.features.demand.repository;

import org.chainoptim.features.demand.model.ClientShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientShipmentRepository extends JpaRepository<ClientShipment, Integer>, ClientShipmentsSearchRepository {

    List<ClientShipment> findByOrganizationId(Integer organizationId);

    @Query("SELECT cs FROM ClientShipment cs " +
            "WHERE cs.clientId = :clientId")
    List<ClientShipment> findByClientId(@Param("clientId") Integer clientId);

    @Query("SELECT COUNT(cs) FROM ClientShipment cs, ClientOrder co WHERE cs.clientOrderId = co.id AND co.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

    @Query("SELECT cs.organizationId FROM ClientShipment cs WHERE cs.id = :shipmentId")
    Optional<Integer> findOrganizationIdById(@Param("shipmentId") Long shipmentId);
}

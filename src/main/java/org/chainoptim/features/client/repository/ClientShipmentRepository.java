package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientShipmentRepository extends JpaRepository<ClientShipment, Integer>, ClientShipmentsSearchRepository {

    List<ClientShipment> findByOrganizationId(Integer organizationId);

    List<ClientShipment> findByClientId(Integer clientId);

    @Query("SELECT cs FROM ClientShipment cs " +
            "WHERE cs.clientOrderId = :orderId")
    List<ClientShipment> findByClientOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT COUNT(cs) FROM ClientShipment cs, ClientOrder co WHERE cs.clientOrderId = co.id AND co.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);
}

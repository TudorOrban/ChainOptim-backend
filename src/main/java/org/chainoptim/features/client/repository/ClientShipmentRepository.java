package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientShipmentRepository extends JpaRepository<ClientShipment, Integer> {

    @Query("SELECT cs FROM ClientShipment cs " +
            "WHERE cs.clientOrderId = :orderId")
    List<ClientShipment> findByClientOrderId(@Param("orderId") Integer orderId);
}

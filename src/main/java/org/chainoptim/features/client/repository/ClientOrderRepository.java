package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {

    List<ClientOrder> findByOrganizationId(Integer organizationId);
    List<ClientOrder> findByClientId(Integer clientId);
}

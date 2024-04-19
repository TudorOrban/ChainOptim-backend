package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer>, ClientOrdersSearchRepository {

    List<ClientOrder> findByOrganizationId(Integer organizationId);
    List<ClientOrder> findByClientId(Integer clientId);

    @Query("SELECT co.organizationId FROM ClientOrder co WHERE co.id = :clientId")
    Optional<Integer> findOrganizationIdById(@Param("clientId") Long clientId);
    @Query("SELECT COUNT(co) FROM ClientOrder co WHERE co.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);
}

package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer>, ClientOrdersSearchRepository {

    List<ClientOrder> findByOrganizationId(Integer organizationId);
    List<ClientOrder> findByClientId(Integer clientId);

    @Query("SELECT COUNT(co) FROM ClientOrder co WHERE co.organizationId = :organizationId")
    long findCountByOrganizationId(@Param("organizationId") Integer organizationId);
}

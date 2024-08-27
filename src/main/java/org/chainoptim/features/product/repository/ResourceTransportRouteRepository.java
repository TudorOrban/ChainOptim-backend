package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.ResourceTransportRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceTransportRouteRepository extends JpaRepository<ResourceTransportRoute, Integer>, TransportRouteSearchRepository {

    List<ResourceTransportRoute> findByOrganizationId(Integer organizationId);

    @Query("SELECT COUNT(r) FROM ResourceTransportRoute r WHERE r.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);
}

package org.chainoptim.features.goods.transportroute.repository;

import org.chainoptim.features.goods.transportroute.model.ResourceTransportRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResourceTransportRouteRepository extends JpaRepository<ResourceTransportRoute, Integer>, TransportRouteSearchRepository {

    List<ResourceTransportRoute> findByOrganizationId(Integer organizationId);

    @Query("SELECT COUNT(r) FROM ResourceTransportRoute r WHERE r.organizationId = :organizationId")
    long countByOrganizationId(@Param("organizationId") Integer organizationId);

    @Query("SELECT r.organizationId FROM ResourceTransportRoute r WHERE r.id = :routeId")
    Optional<Integer> findOrganizationIdById(@Param("routeId") Long routeId);
}

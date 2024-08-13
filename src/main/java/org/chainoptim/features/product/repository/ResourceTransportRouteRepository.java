package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.ResourceTransportRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceTransportRouteRepository extends JpaRepository<ResourceTransportRoute, Integer> {

    List<ResourceTransportRoute> findByOrganizationId(Integer organizationId);
}

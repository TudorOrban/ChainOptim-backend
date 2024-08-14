package org.chainoptim.features.product.service;

import org.chainoptim.features.product.dto.CreateRouteDTO;
import org.chainoptim.features.product.dto.UpdateRouteDTO;
import org.chainoptim.features.product.model.ResourceTransportRoute;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface ResourceTransportRouteService {

    List<ResourceTransportRoute> getRoutesByOrganizationId(Integer organizationId);
    PaginatedResults<ResourceTransportRoute> getRoutesByOrganizationIdAdvanced(Integer organizationId, SearchParams searchParams);
    ResourceTransportRoute createRoute(CreateRouteDTO routeDTO);
    ResourceTransportRoute updateRoute(UpdateRouteDTO routeDTO);
    void deleteRoute(Integer routeId);
}

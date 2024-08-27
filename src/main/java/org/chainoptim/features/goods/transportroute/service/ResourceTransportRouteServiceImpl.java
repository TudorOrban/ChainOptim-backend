package org.chainoptim.features.goods.transportroute.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.goods.transportroute.dto.CreateRouteDTO;
import org.chainoptim.features.goods.transportroute.dto.UpdateRouteDTO;
import org.chainoptim.features.goods.transportroute.model.ResourceTransportRoute;
import org.chainoptim.features.goods.transportroute.repository.ResourceTransportRouteRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceTransportRouteServiceImpl implements ResourceTransportRouteService {

    private final ResourceTransportRouteRepository routeRepository;
    private final SubscriptionPlanLimiterService planLimiterService;

    @Autowired
    public ResourceTransportRouteServiceImpl(ResourceTransportRouteRepository routeRepository,
                                             SubscriptionPlanLimiterService planLimiterService) {
        this.routeRepository = routeRepository;
        this.planLimiterService = planLimiterService;
    }

    public List<ResourceTransportRoute> getRoutesByOrganizationId(Integer organizationId) {
        return routeRepository.findByOrganizationId(organizationId);
    }

    public PaginatedResults<ResourceTransportRoute> getRoutesByOrganizationIdAdvanced(Integer organizationId, SearchParams searchParams) {
        return routeRepository.findByOrganizationIdAdvanced(organizationId, searchParams);
    }

    public ResourceTransportRoute createRoute(CreateRouteDTO routeDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(routeDTO.getOrganizationId(), Feature.TRANSPORT_ROUTE, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed transport routes for the current Subscription Plan.");
        }

        ResourceTransportRoute route = new ResourceTransportRoute();
        route.setOrganizationId(routeDTO.getOrganizationId());
        route.setTransportRoute(routeDTO.getTransportRoute());
        route.setCompanyId(routeDTO.getCompanyId());

        return routeRepository.save(route);
    }

    public ResourceTransportRoute updateRoute(UpdateRouteDTO routeDTO) {
        ResourceTransportRoute route = routeRepository.findById(routeDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Route with ID: " + routeDTO.getId() + " not found"));

        route.setTransportRoute(routeDTO.getTransportRoute());

        return routeRepository.save(route);
    }

    public void deleteRoute(Integer routeId) {
        routeRepository.deleteById(routeId);
    }
}

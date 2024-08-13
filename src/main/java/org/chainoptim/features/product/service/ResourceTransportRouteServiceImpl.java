package org.chainoptim.features.product.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.dto.CreateRouteDTO;
import org.chainoptim.features.product.dto.UpdateRouteDTO;
import org.chainoptim.features.product.model.ResourceTransportRoute;
import org.chainoptim.features.product.repository.ResourceTransportRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceTransportRouteServiceImpl implements ResourceTransportRouteService {

    private final ResourceTransportRouteRepository routeRepository;

    @Autowired
    public ResourceTransportRouteServiceImpl(ResourceTransportRouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<ResourceTransportRoute> getRoutesByOrganizationId(Integer organizationId) {
        return routeRepository.findByOrganizationId(organizationId);
    }

    public ResourceTransportRoute createRoute(CreateRouteDTO routeDTO) {
        ResourceTransportRoute route = new ResourceTransportRoute();
        route.setOrganizationId(routeDTO.getOrganizationId());
        route.setTransportRoute(routeDTO.getTransportRoute());

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

package org.chainoptim.features.product.controller;

import org.chainoptim.features.product.dto.CreateRouteDTO;
import org.chainoptim.features.product.dto.UpdateRouteDTO;
import org.chainoptim.features.product.model.ResourceTransportRoute;
import org.chainoptim.features.product.service.ResourceTransportRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource-transport-routes")
public class ResourceTransportRouteController {

    private final ResourceTransportRouteService routeService;

    @Autowired
    public ResourceTransportRouteController(ResourceTransportRouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<ResourceTransportRoute>> getRoutesByOrganizationId(@PathVariable Integer organizationId) {
        List<ResourceTransportRoute> routes = routeService.getRoutesByOrganizationId(organizationId);
        return ResponseEntity.ok(routes);
    }

    @PostMapping("/create")
    public ResponseEntity<ResourceTransportRoute> createRoute(@RequestBody CreateRouteDTO routeDTO) {
        ResourceTransportRoute createdRoute = routeService.createRoute(routeDTO);
        return ResponseEntity.ok(createdRoute);
    }

    @PutMapping("/update")
    public ResponseEntity<ResourceTransportRoute> updateRoute(@RequestBody UpdateRouteDTO routeDTO) {
        ResourceTransportRoute updatedRoute = routeService.updateRoute(routeDTO);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/delete/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable("routeId") Integer routeId) {
        routeService.deleteRoute(routeId);
        return ResponseEntity.ok().build();
    }
}

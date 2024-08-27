package org.chainoptim.features.goods.transportroute.controller;

import org.chainoptim.features.goods.transportroute.dto.CreateRouteDTO;
import org.chainoptim.features.goods.transportroute.dto.UpdateRouteDTO;
import org.chainoptim.features.goods.transportroute.model.ResourceTransportRoute;
import org.chainoptim.features.goods.transportroute.service.ResourceTransportRouteService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource-transport-routes")
public class ResourceTransportRouteController { // TODO: Secure endpoints

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

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Product\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<ResourceTransportRoute>> getProductsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        SearchParams searchParams = new SearchParams(searchQuery, "", null, sortBy, ascending, page, itemsPerPage);
        PaginatedResults<ResourceTransportRoute> paginatedResults = routeService.getRoutesByOrganizationIdAdvanced(organizationId, searchParams);
        return ResponseEntity.ok(paginatedResults);
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

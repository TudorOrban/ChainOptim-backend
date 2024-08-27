package org.chainoptim.features.goods.component.controller;

import org.chainoptim.core.general.security.service.SecurityService;
import org.chainoptim.features.goods.component.dto.ComponentsSearchDTO;
import org.chainoptim.features.goods.component.dto.CreateComponentDTO;
import org.chainoptim.features.goods.controller.UpdateComponentDTO;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.features.goods.component.service.ComponentService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/components")
public class ComponentController {

    private final ComponentService componentService;
    private final SecurityService securityService;

    @Autowired
    public ComponentController(ComponentService componentService,
                               SecurityService securityService) {
        this.componentService = componentService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Component\", \"Read\")")
    @GetMapping("/organization/{organizationId}/small")
    public ResponseEntity<List<ComponentsSearchDTO>> getComponentsByOrganizationIdSmall(@PathVariable Integer organizationId) {
        List<ComponentsSearchDTO> components = componentService.getComponentsByOrganizationIdSmall(organizationId);
        return ResponseEntity.ok(components);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Component\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Component>> getComponentsByOrganizationId(@PathVariable Integer organizationId) {
        List<Component> components = componentService.getComponentsByOrganizationId(organizationId);
        return ResponseEntity.ok(components);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Component\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<ComponentsSearchDTO>> getProductsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<ComponentsSearchDTO> paginatedResults = componentService.getComponentsByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(paginatedResults);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable Integer id) {
        Component component = componentService.getComponentById(id);
        return ResponseEntity.ok(component);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#componentDTO.getOrganizationId(), \"Component\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Component> createComponent(@RequestBody CreateComponentDTO componentDTO) {
        Component component =  componentService.createComponent(componentDTO);
        return ResponseEntity.ok(component);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#componentDTO.getId(), \"Component\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Component> updateComponent(@RequestBody UpdateComponentDTO componentDTO) {
        Component component =  componentService.updateComponent(componentDTO);
        return ResponseEntity.ok(component);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#id, \"Component\", \"Delete\")")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Integer id) {
        componentService.deleteComponent(id);
        return ResponseEntity.ok().build();
    }

}

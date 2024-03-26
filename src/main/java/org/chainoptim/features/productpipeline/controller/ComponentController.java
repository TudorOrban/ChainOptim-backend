package org.chainoptim.features.productpipeline.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.productpipeline.dto.CreateComponentDTO;
import org.chainoptim.features.productpipeline.dto.UpdateComponentDTO;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.service.ComponentService;
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
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Component>> getComponentsByOrganizationId(@PathVariable Integer organizationId) {
        List<Component> components = componentService.getComponentsByOrganizationId(organizationId);
        return ResponseEntity.ok(components);
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

package com.TudorAOrban.chainoptimizer.organization.controller;

import com.TudorAOrban.chainoptimizer.dto.OrganizationDTO;
import com.TudorAOrban.chainoptimizer.organization.model.Organization;
import com.TudorAOrban.chainoptimizer.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.createOrganization(organization));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Integer id,
                                                               @RequestParam(defaultValue = "false") boolean includeUsers) {
        OrganizationDTO organizationDTO = organizationService.getOrganizationById(id, includeUsers);
        if (organizationDTO != null) {
            return ResponseEntity.ok(organizationDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @PutMapping
    public ResponseEntity<Organization> updateOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.updateOrganization(organization));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Integer id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.ok().build();
    }
}

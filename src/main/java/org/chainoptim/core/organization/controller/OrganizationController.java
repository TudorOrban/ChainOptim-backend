package org.chainoptim.core.organization.controller;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.dto.CreateOrganizationDTO;
import org.chainoptim.core.organization.dto.OrganizationDTO;
import org.chainoptim.core.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody CreateOrganizationDTO createOrganizationDTO) {
        Organization createdOrganization = organizationService.createOrganization(createOrganizationDTO);
        return ResponseEntity.ok(createdOrganization);
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

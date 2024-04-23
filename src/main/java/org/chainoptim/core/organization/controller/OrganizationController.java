package org.chainoptim.core.organization.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.core.organization.dto.UpdateOrganizationDTO;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.dto.CreateOrganizationDTO;
import org.chainoptim.core.organization.dto.OrganizationDTO;
import org.chainoptim.core.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final SecurityService securityService;

    @Autowired
    public OrganizationController(OrganizationService organizationService,
                                  SecurityService securityService) {
        this.organizationService = organizationService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#id, \"Organization\", \"Read\")")
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

    @PostMapping("/create")
    public ResponseEntity<Organization> createOrganization(@RequestBody CreateOrganizationDTO createOrganizationDTO) {
        Organization createdOrganization = organizationService.createOrganization(createOrganizationDTO);
        return ResponseEntity.ok(createdOrganization);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationDTO.getId(), \"Organization\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Organization> updateOrganization(@RequestBody UpdateOrganizationDTO organizationDTO) {
        return ResponseEntity.ok(organizationService.updateOrganization(organizationDTO));
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#id, \"Organization\", \"Delete\")")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Integer id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.ok().build();
    }
}

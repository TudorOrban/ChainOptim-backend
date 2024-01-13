package org.chainoptim.core.organization.controller;

import org.chainoptim.core.organization.service.OrganizationInviteService;
import org.chainoptim.core.organization.dto.CreateOrganizationInviteDTO;
import org.chainoptim.core.organization.model.OrganizationInvite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization-invites")
public class OrganizationInviteController {

    @Autowired
    private OrganizationInviteService organizationInviteService;

    @PostMapping
    public ResponseEntity<OrganizationInvite> createOrganizationInvite(@RequestBody CreateOrganizationInviteDTO createOrganizationInviteDTO) {
        return ResponseEntity.ok(organizationInviteService.createOrganizationInvite(createOrganizationInviteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationInvite> getOrganizationInviteById(@PathVariable Integer id) {
        OrganizationInvite organizationInvite = organizationInviteService.getOrganizationInviteById(id);
        if (organizationInvite != null) {
            return ResponseEntity.ok(organizationInvite);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<OrganizationInvite> getAllOrganizationInvites() {
        return organizationInviteService.getAllOrganizationInvites();
    }

    @PutMapping
    public ResponseEntity<OrganizationInvite> updateOrganizationInvite(@RequestBody OrganizationInvite organizationInvite) {
        return ResponseEntity.ok(organizationInviteService.updateOrganizationInvite(organizationInvite));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationInvite(@PathVariable Integer id) {
        organizationInviteService.deleteOrganizationInvite(id);
        return ResponseEntity.ok().build();
    }
}

package com.TudorAOrban.chainoptimizer.organization.controller;

import com.TudorAOrban.chainoptimizer.dto.CreateOrganizationInviteDTO;
import com.TudorAOrban.chainoptimizer.organization.model.OrganizationInvite;
import com.TudorAOrban.chainoptimizer.organization.service.OrganizationInviteService;
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

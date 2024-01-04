package com.TudorAOrban.chainoptimizer.organization.controller;

import com.TudorAOrban.chainoptimizer.organization.model.OrganizationRequest;
import com.TudorAOrban.chainoptimizer.organization.service.OrganizationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization-requests")
public class OrganizationRequestController {

    @Autowired
    private OrganizationRequestService organizationRequestService;

    @PostMapping
    public ResponseEntity<OrganizationRequest> createOrganizationRequest(@RequestBody OrganizationRequest organizationRequest) {
        return ResponseEntity.ok(organizationRequestService.createOrganizationRequest(organizationRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationRequest> getOrganizationRequestById(@PathVariable Integer id) {
        OrganizationRequest organizationRequest = organizationRequestService.getOrganizationRequestById(id);
        if (organizationRequest != null) {
            return ResponseEntity.ok(organizationRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<OrganizationRequest> getAllOrganizationRequests() {
        return organizationRequestService.getAllOrganizationRequests();
    }

    @PutMapping
    public ResponseEntity<OrganizationRequest> updateOrganizationRequest(@RequestBody OrganizationRequest organizationRequest) {
        return ResponseEntity.ok(organizationRequestService.updateOrganizationRequest(organizationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationRequest(@PathVariable Integer id) {
        organizationRequestService.deleteOrganizationRequest(id);
        return ResponseEntity.ok().build();
    }
}

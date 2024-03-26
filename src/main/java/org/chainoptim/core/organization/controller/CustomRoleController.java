package org.chainoptim.core.organization.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.core.organization.dto.CreateCustomRoleDTO;
import org.chainoptim.core.organization.dto.UpdateCustomRoleDTO;
import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.organization.service.CustomRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/custom-roles")
public class CustomRoleController {

    private final CustomRoleService customRoleService;
    private final SecurityService securityService;

    @Autowired
    public CustomRoleController(CustomRoleService customRoleService,
                                SecurityService securityService) {
        this.customRoleService = customRoleService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"CustomRole\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<CustomRole>> getCustomRolesByOrganizationId(@PathVariable("organizationId") Integer organizationId) {
        List<CustomRole> customRoles = customRoleService.getCustomRolesByOrganizationId(organizationId);
        return ResponseEntity.ok(customRoles);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#roleDTO.getOrganizationId(), \"CustomRole\", \"Create\")")
    @PostMapping("create")
    public ResponseEntity<CustomRole> createCustomRole(@RequestBody CreateCustomRoleDTO roleDTO) {
        CustomRole customRole = customRoleService.createCustomRole(roleDTO);
        return ResponseEntity.ok(customRole);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#roleDTO.getId(), \"CustomRole\", \"Update\")")
    @PutMapping("update")
    public ResponseEntity<CustomRole> updateCustomRole(@RequestBody UpdateCustomRoleDTO roleDTO) {
        CustomRole customRole = customRoleService.updateCustomRole(roleDTO.getId(), roleDTO);
        return ResponseEntity.ok(customRole);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#roleId, \"CustomRole\", \"Delete\")")
    @DeleteMapping("delete/{roleId}")
    public ResponseEntity<Void> deleteCustomRole(@PathVariable("roleId") Integer roleId) {
        customRoleService.deleteCustomRole(roleId);
        return ResponseEntity.ok().build();
    }
}

package org.chainoptim.features.factory.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.service.FactoryService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factories")
public class FactoryController {

    private final FactoryService factoryService;
    private final SecurityService securityService;

    @Autowired
    public FactoryController(
            FactoryService factoryService,
            SecurityService securityService
    ) {
        this.factoryService = factoryService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId)")
    @GetMapping("/organization/{organizationId}/small")
    public ResponseEntity<List<FactoriesSearchDTO>> getFactoriesByOrganizationIdSmall(@PathVariable Integer organizationId) {
        List<FactoriesSearchDTO> factories = factoryService.getFactoriesByOrganizationIdSmall(organizationId);
        if (factories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factories);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId)")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Factory>> getFactoriesByOrganizationId(@PathVariable Integer organizationId) {
        List<Factory> factories = factoryService.getFactoriesByOrganizationId(organizationId);
        if (factories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factories);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId)")
    @GetMapping("/organizations/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<FactoriesSearchDTO>> getFactoriesByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage) {
        PaginatedResults<FactoriesSearchDTO> factories = factoryService.getFactoriesByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(factories);
    }

    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    @GetMapping("/{factoryId}")
    public ResponseEntity<Factory> getFactoryById(@PathVariable Integer factoryId) {
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        return ResponseEntity.ok(factory);
    }

    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    @GetMapping("/{factoryId}/stages")
    public ResponseEntity<Factory> getFactoryWithStages(@PathVariable Integer factoryId) {
        Factory factory = factoryService.getFactoryWithStagesById(factoryId);
        if (factory == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(factory);
        }
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#factoryDTO.getOrganizationId())")
    @PostMapping("/create")
    public ResponseEntity<Factory> createFactory(@RequestBody CreateFactoryDTO factoryDTO) {
        Factory factory = factoryService.createFactory(factoryDTO);
        return ResponseEntity.ok(factory);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#factoryDTO.getId(), \"Factory\")")
    @PutMapping("/update")
    public ResponseEntity<Factory> updateFactory(@RequestBody UpdateFactoryDTO factoryDTO) {
        Factory factory = factoryService.updateFactory(factoryDTO);
        return ResponseEntity.ok(factory);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\")")
    @DeleteMapping("/delete/{factoryId}")
    public ResponseEntity<Void> deleteFactory(@PathVariable Integer factoryId) {
        factoryService.deleteFactory(factoryId);
        return ResponseEntity.ok().build();
    }
}
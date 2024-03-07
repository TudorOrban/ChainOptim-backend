package org.chainoptim.features.factory.controller;

import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.service.FactoryService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factories")
public class FactoryController {

    private final FactoryService factoryService;

    @Autowired
    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    // Fetch
    @GetMapping("/{id}")
    public ResponseEntity<Factory> getFactoryById(@PathVariable Integer id) {
        return factoryService.getFactoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Factory>> getFactoriesByOrganizationId(@PathVariable Integer organizationId) {
        List<Factory> factories = factoryService.getFactoriesByOrganizationId(organizationId);
        if (factories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factories);
    }

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

    // Create
    @PostMapping("/create")
    public ResponseEntity<Factory> createFactory(@RequestBody CreateFactoryDTO factoryDTO) {
        Factory factory = factoryService.createFactory(factoryDTO);
        return ResponseEntity.ok(factory);
    }

    // Update
    @PutMapping("/update")
    public ResponseEntity<Factory> updateFactory(@RequestBody UpdateFactoryDTO factoryDTO) {
        Factory factory = factoryService.updateFactory(factoryDTO);
        return ResponseEntity.ok(factory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFactory(@PathVariable Integer id) {
        factoryService.deleteFactory(id);
        return ResponseEntity.ok().build();
    }
}
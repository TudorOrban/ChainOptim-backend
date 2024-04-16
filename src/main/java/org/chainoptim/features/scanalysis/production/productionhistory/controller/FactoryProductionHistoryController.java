package org.chainoptim.features.scanalysis.production.productionhistory.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.AddDayToFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.CreateFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.dto.UpdateFactoryProductionHistoryDTO;
import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;
import org.chainoptim.features.scanalysis.production.productionhistory.service.FactoryProductionHistoryPersistenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/factory-production-histories")
public class FactoryProductionHistoryController {

    private final FactoryProductionHistoryPersistenceService historyPersistenceService;
    private final SecurityService securityService;

    @Autowired
    public FactoryProductionHistoryController(
            FactoryProductionHistoryPersistenceService historyPersistenceService,
            SecurityService securityService
    ) {
        this.historyPersistenceService = historyPersistenceService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessEntity(#factoryId, \"Factory\", \"Read\")")
    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<FactoryProductionHistory> getFactoryProductionHistory(@PathVariable Integer factoryId) {
        FactoryProductionHistory factoryProductionHistory = historyPersistenceService.getFactoryProductionHistoryByFactoryId(factoryId);
        return ResponseEntity.ok(factoryProductionHistory);
    }

    @GetMapping("/factory/{factoryId}/id")
    public ResponseEntity<Integer> getFactoryProductionHistoryId(@PathVariable Integer factoryId) {
        Integer id = historyPersistenceService.getIdByFactoryId(factoryId);
        return ResponseEntity.ok(id);
    }

    // Create
    @PreAuthorize("@securityService.canAccessEntity(#historyDTO.getFactoryId(), \"Factory\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<FactoryProductionHistory> createFactoryProductionHistory(@RequestBody CreateFactoryProductionHistoryDTO historyDTO) {
        FactoryProductionHistory createdHistory = historyPersistenceService.createFactoryProductionHistory(historyDTO);
        return ResponseEntity.ok(createdHistory);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#historyDTO.getFactoryId(), \"Factory\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<FactoryProductionHistory> updateFactoryProductionHistory(@RequestBody UpdateFactoryProductionHistoryDTO historyDTO) {
        FactoryProductionHistory updatedHistory = historyPersistenceService.updateFactoryProductionHistory(historyDTO);
        return ResponseEntity.ok(updatedHistory);
    }

    @PreAuthorize("@securityService.canAccessEntity(#addDayDTO.getFactoryId(), \"Factory\", \"Update\")")
    @PutMapping("/add-day")
    public ResponseEntity<FactoryProductionHistory> addDayToFactoryProductionHistory(@RequestBody AddDayToFactoryProductionHistoryDTO addDayDTO) {
        FactoryProductionHistory updatedHistory = historyPersistenceService.addDayToFactoryProductionHistory(addDayDTO);
        return ResponseEntity.ok(updatedHistory);
    }

    // Delete
    // TODO: Secure this endpoint
    // @PreAuthorize("@securityService.canAccessEntity(#id, \"Factory\", \"Delete\")")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFactoryProductionHistory(@PathVariable Integer id) {
        historyPersistenceService.deleteFactoryProductionHistory(id);
        return ResponseEntity.noContent().build();
    }
}

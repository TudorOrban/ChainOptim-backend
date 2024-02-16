package org.chainoptim.features.factory.controller;

import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/factories")
public class FactoryController {

    private final FactoryService factoryService;

    @Autowired
    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @GetMapping
    public ResponseEntity<List<Factory>> getAllFactories() {
        List<Factory> factories = factoryService.getAllFactories();
        return ResponseEntity.ok(factories);
    }

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
}
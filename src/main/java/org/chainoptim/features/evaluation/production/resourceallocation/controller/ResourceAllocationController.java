package org.chainoptim.features.evaluation.production.resourceallocation.controller;

import org.chainoptim.features.evaluation.production.graph.model.FactoryGraph;
import org.chainoptim.features.evaluation.production.graph.model.FactoryProductionGraph;
import org.chainoptim.features.evaluation.production.graph.service.FactoryProductionGraphService;
import org.chainoptim.features.evaluation.production.resourceallocation.model.AllocationPlan;
import org.chainoptim.features.evaluation.production.resourceallocation.service.ResourceAllocatorService;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.factory.service.FactoryInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/factories/allocate-resources")
public class ResourceAllocationController {

    private final ResourceAllocatorService resourceAllocatorService;
    private final FactoryProductionGraphService graphService;
    private final FactoryInventoryService factoryInventoryService;

    @Autowired
    public ResourceAllocationController(ResourceAllocatorService resourceAllocatorService,
                                        FactoryProductionGraphService factoryProductionGraphService,
                                        FactoryInventoryService factoryInventoryService) {
        this.resourceAllocatorService = resourceAllocatorService;
        this.graphService = factoryProductionGraphService;
        this.factoryInventoryService = factoryInventoryService;
    }

    /*
     * To be refactored
     */
    @PostMapping("/{factoryId}")
    public ResponseEntity<AllocationPlan> allocateResources(@PathVariable("factoryId") Integer factoryId, @RequestBody Float duration) {
        List<FactoryProductionGraph> graphs = graphService.getProductionGraphByFactoryId(factoryId);
        List<FactoryInventoryItem> inventory = factoryInventoryService.getFactoryInventoryItemsByFactoryId(factoryId);
        Map<Integer, FactoryInventoryItem> inventoryMap = inventory.stream()
                .filter(item -> item.getComponent() != null) // Only include items with a non-null component
                .collect(Collectors.toMap(item -> item.getComponent().getId(), item -> item));
        AllocationPlan allocationPlan = resourceAllocatorService.allocateResourcesFromInventory(graphs.getFirst().getFactoryGraph(), inventoryMap, duration);

        return ResponseEntity.ok(allocationPlan);
    }
}

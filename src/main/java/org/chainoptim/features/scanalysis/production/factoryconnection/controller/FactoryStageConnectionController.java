package org.chainoptim.features.scanalysis.production.factoryconnection.controller;

import org.chainoptim.features.scanalysis.production.factoryconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.scanalysis.production.factoryconnection.service.FactoryStageConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factory-stage-connections")
public class FactoryStageConnectionController {

    private final FactoryStageConnectionService factoryStageConnectionService;

    @Autowired
    public FactoryStageConnectionController(FactoryStageConnectionService factoryStageConnectionService) {
        this.factoryStageConnectionService = factoryStageConnectionService;
    }

    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<List<FactoryStageConnection>> getConnectionsByFactoryId(@PathVariable Integer factoryId) {
        return ResponseEntity.ok(factoryStageConnectionService.getConnectionsByFactoryId(factoryId));
    }

    @PostMapping("/create")
    public ResponseEntity<FactoryStageConnection> createConnection(@RequestBody CreateConnectionDTO connectionDTO) {
        return ResponseEntity.ok(factoryStageConnectionService.createConnection(connectionDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<FactoryStageConnection> updateConnection(@RequestBody UpdateConnectionDTO connectionDTO) {
        return ResponseEntity.ok(factoryStageConnectionService.updateConnection(connectionDTO.getId(), connectionDTO));
    }

    @DeleteMapping("/delete/{connectionId}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Integer connectionId) {
        factoryStageConnectionService.deleteConnection(connectionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> findAndDeleteConnection(@RequestBody DeleteConnectionDTO connectionDTO) {
        factoryStageConnectionService.findAndDeleteConnection(connectionDTO);
        return ResponseEntity.ok().build();
    }
}

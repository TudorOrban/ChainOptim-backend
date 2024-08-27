package org.chainoptim.features.production.stageconnection.controller;

import org.chainoptim.features.production.stageconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.production.stageconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.production.stageconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.production.stageconnection.service.FactoryStageConnectionService;
import org.chainoptim.features.production.stageconnection.model.FactoryStageConnection;
import org.chainoptim.features.production.stageconnection.service.FactoryStageConnectionWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/factory-stage-connections")
public class FactoryStageConnectionController {

    private final FactoryStageConnectionService factoryStageConnectionService;
    private final FactoryStageConnectionWriteService factoryStageConnectionWriteService;

    @Autowired
    public FactoryStageConnectionController(FactoryStageConnectionService factoryStageConnectionService,
                                            FactoryStageConnectionWriteService factoryStageConnectionWriteService) {
        this.factoryStageConnectionService = factoryStageConnectionService;
        this.factoryStageConnectionWriteService = factoryStageConnectionWriteService;
    }

    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<List<FactoryStageConnection>> getConnectionsByFactoryId(@PathVariable Integer factoryId) {
        return ResponseEntity.ok(factoryStageConnectionService.getConnectionsByFactoryId(factoryId));
    }

    @PostMapping("/create")
    public ResponseEntity<FactoryStageConnection> createConnection(@RequestBody CreateConnectionDTO connectionDTO) {
        return ResponseEntity.ok(factoryStageConnectionWriteService.createConnection(connectionDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<FactoryStageConnection> updateConnection(@RequestBody UpdateConnectionDTO connectionDTO) {
        return ResponseEntity.ok(factoryStageConnectionWriteService.updateConnection(connectionDTO.getId(), connectionDTO));
    }

    @DeleteMapping("/delete/{connectionId}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Integer connectionId) {
        factoryStageConnectionWriteService.deleteConnection(connectionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> findAndDeleteConnection(@RequestBody DeleteConnectionDTO connectionDTO) {
        factoryStageConnectionWriteService.findAndDeleteConnection(connectionDTO);
        return ResponseEntity.ok().build();
    }
}

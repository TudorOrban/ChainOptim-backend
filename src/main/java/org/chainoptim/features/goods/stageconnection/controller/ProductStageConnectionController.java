package org.chainoptim.features.goods.stageconnection.controller;

import org.chainoptim.features.goods.stageconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.goods.stageconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.goods.stageconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.goods.stageconnection.model.ProductStageConnection;
import org.chainoptim.features.goods.stageconnection.service.StageConnectionService;
import org.chainoptim.features.goods.stageconnection.service.StageConnectionWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-stage-connections")
public class ProductStageConnectionController {

    private final StageConnectionService stageConnectionService;
    private final StageConnectionWriteService stageConnectionWriteService;

    @Autowired
    public ProductStageConnectionController(StageConnectionService stageConnectionService,
                                            StageConnectionWriteService stageConnectionWriteService) {
        this.stageConnectionService = stageConnectionService;
        this.stageConnectionWriteService = stageConnectionWriteService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductStageConnection>> getConnectionsByProductId(@PathVariable Integer productId) {
        return ResponseEntity.ok(stageConnectionService.getConnectionsByProductId(productId));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductStageConnection> createConnection(@RequestBody CreateConnectionDTO connectionDTO) {
        return ResponseEntity.ok(stageConnectionWriteService.createConnection(connectionDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductStageConnection> updateConnection(@RequestBody UpdateConnectionDTO connectionDTO) {
        return ResponseEntity.ok(stageConnectionWriteService.updateConnection(connectionDTO.getId(), connectionDTO));
    }

    @DeleteMapping("/delete/{connectionId}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Integer connectionId) {
        stageConnectionWriteService.deleteConnection(connectionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> findAndDeleteConnection(@RequestBody DeleteConnectionDTO connectionDTO) {
        stageConnectionWriteService.findAndDeleteConnection(connectionDTO);
        return ResponseEntity.ok().build();
    }
}

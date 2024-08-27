package org.chainoptim.features.goods.stage.controller;

import org.chainoptim.features.goods.stage.dto.CreateStageInputDTO;
import org.chainoptim.features.goods.stage.dto.DeleteStageInputDTO;
import org.chainoptim.features.goods.stage.dto.UpdateStageInputDTO;
import org.chainoptim.features.goods.stage.model.StageInput;
import org.chainoptim.features.goods.stage.service.StageInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stage-inputs")
public class StageInputController {

    private final StageInputService stageInputService;

    @Autowired
    public StageInputController(StageInputService stageInputService) {
        this.stageInputService = stageInputService;
    }

    @GetMapping("/stage/{stageId}")
    public List<StageInput> getStageInputsByStageId(@PathVariable Integer stageId) {
        return stageInputService.getStageInputsByStageId(stageId);
    }

    @GetMapping("/{id}")
    public StageInput getStageInputById(@PathVariable Integer id) {
        return stageInputService.getStageInputById(id);
    }

    @PostMapping("/create")
    public StageInput createStageInput(@RequestBody CreateStageInputDTO inputDTO) {
        return stageInputService.createStageInput(inputDTO);
    }

    @PutMapping("/update")
    public StageInput updateStageInput(@RequestBody UpdateStageInputDTO inputDTO) {
        return stageInputService.updateStageInput(inputDTO);
    }

    @DeleteMapping("/delete")
    public void deleteStageInput(@RequestBody DeleteStageInputDTO inputDTO) {
        stageInputService.deleteStageInput(inputDTO);
    }
}

package org.chainoptim.features.goods.stage.controller;


import org.chainoptim.features.goods.stage.dto.CreateStageOutputDTO;
import org.chainoptim.features.goods.stage.dto.DeleteStageOutputDTO;
import org.chainoptim.features.goods.stage.dto.UpdateStageOutputDTO;
import org.chainoptim.features.goods.stage.model.StageOutput;
import org.chainoptim.features.goods.stage.service.StageOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stage-outputs")
public class StageOutputController {

    private final StageOutputService stageOutputService;

    @Autowired
    public StageOutputController(StageOutputService stageOutputService) {
        this.stageOutputService = stageOutputService;
    }

    @GetMapping("/stage/{stageId}")
    public List<StageOutput> getStageOutputsByStageId(@PathVariable Integer stageId) {
        return stageOutputService.getStageOutputsByStageId(stageId);
    }

    @GetMapping("/{id}")
    public StageOutput getStageOutputById(@PathVariable Integer id) {
        return stageOutputService.getStageOutputById(id);
    }

    @PostMapping("/create")
    public StageOutput createStageOutput(@RequestBody CreateStageOutputDTO outputDTO) {
        return stageOutputService.createStageOutput(outputDTO);
    }

    @PutMapping("/update")
    public StageOutput updateStageOutput(@RequestBody UpdateStageOutputDTO outputDTO) {
        return stageOutputService.updateStageOutput(outputDTO);
    }

    @DeleteMapping("/delete")
    public void deleteStageOutput(@RequestBody DeleteStageOutputDTO outputDTO) {
        stageOutputService.deleteStageOutput(outputDTO);
    }
}

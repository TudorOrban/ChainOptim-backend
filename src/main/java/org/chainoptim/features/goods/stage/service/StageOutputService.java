package org.chainoptim.features.goods.stage.service;

import org.chainoptim.features.goods.stage.dto.CreateStageOutputDTO;
import org.chainoptim.features.goods.stage.dto.DeleteStageOutputDTO;
import org.chainoptim.features.goods.stage.dto.UpdateStageOutputDTO;
import org.chainoptim.features.goods.stage.model.StageOutput;

import java.util.List;

public interface StageOutputService {

    List<StageOutput> getStageOutputsByStageId(Integer stageId);
    StageOutput getStageOutputById(Integer id);
    StageOutput createStageOutput(CreateStageOutputDTO outputDTO);
    StageOutput updateStageOutput(UpdateStageOutputDTO outputDTO);
    void deleteStageOutput(DeleteStageOutputDTO outputDTO);
}

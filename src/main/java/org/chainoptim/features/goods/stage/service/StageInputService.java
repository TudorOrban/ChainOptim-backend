package org.chainoptim.features.goods.stage.service;

import org.chainoptim.features.goods.stage.dto.CreateStageInputDTO;
import org.chainoptim.features.goods.stage.dto.DeleteStageInputDTO;
import org.chainoptim.features.goods.stage.dto.UpdateStageInputDTO;
import org.chainoptim.features.goods.stage.model.StageInput;

import java.util.List;

public interface StageInputService {

    List<StageInput> getStageInputsByStageId(Integer stageId);
    StageInput getStageInputById(Integer id);
    StageInput createStageInput(CreateStageInputDTO inputDTO);
    StageInput updateStageInput(UpdateStageInputDTO inputDTO);
    void deleteStageInput(DeleteStageInputDTO inputDTO);
}

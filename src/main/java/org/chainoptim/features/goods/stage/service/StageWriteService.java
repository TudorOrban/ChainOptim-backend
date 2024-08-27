package org.chainoptim.features.goods.stage.service;

import org.chainoptim.features.goods.stage.dto.CreateStageDTO;
import org.chainoptim.features.goods.stage.dto.UpdateStageDTO;
import org.chainoptim.features.goods.stage.model.Stage;

public interface StageWriteService {

    // Create
    Stage createStage(CreateStageDTO stageDTO);

    // Update
    Stage updateStage(UpdateStageDTO stageDTO);

    // Delete
    void deleteStage(Integer stageId);
}

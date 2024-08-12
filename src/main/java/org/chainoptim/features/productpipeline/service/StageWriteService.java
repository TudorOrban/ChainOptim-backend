package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.productpipeline.model.Stage;

public interface StageWriteService {

    // Create
    Stage createStage(CreateStageDTO stageDTO);

    // Update
    Stage updateStage(UpdateStageDTO stageDTO);

    // Delete
    void deleteStage(Integer stageId);
}

package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.productpipeline.model.Stage;

import java.util.List;
import java.util.Optional;

public interface StageService {

    Stage getStageById(Integer stageId);
    List<Stage> getStagesByProductId(Integer productId);
    Stage createStage(CreateStageDTO stageDTO);
    Stage updateStage(UpdateStageDTO stageDTO);
    void deleteStage(Integer stageId);
}

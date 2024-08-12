package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateStageInputDTO;
import org.chainoptim.features.productpipeline.dto.DeleteStageInputDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageInputDTO;
import org.chainoptim.features.productpipeline.model.StageInput;

import java.util.List;

public interface StageInputService {

    List<StageInput> getStageInputsByStageId(Integer stageId);
    StageInput createStageInput(CreateStageInputDTO inputDTO);
    StageInput updateStageInput(UpdateStageInputDTO inputDTO);
    void deleteStageInput(DeleteStageInputDTO inputDTO);
}

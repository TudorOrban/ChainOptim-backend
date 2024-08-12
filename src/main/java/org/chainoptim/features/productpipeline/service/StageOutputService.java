package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateStageOutputDTO;
import org.chainoptim.features.productpipeline.dto.DeleteStageOutputDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageOutputDTO;
import org.chainoptim.features.productpipeline.model.StageOutput;

import java.util.List;

public interface StageOutputService {

    List<StageOutput> getStageOutputsByStageId(Integer stageId);
    StageOutput createStageOutput(CreateStageOutputDTO outputDTO);
    StageOutput updateStageOutput(UpdateStageOutputDTO outputDTO);
    void deleteStageOutput(DeleteStageOutputDTO outputDTO);
}

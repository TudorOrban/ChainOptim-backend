package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.StagesSearchDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.productpipeline.model.Stage;

import java.util.List;
import java.util.Optional;

public interface StageService {

    // Fetch
    List<StagesSearchDTO> getStagesByOrganizationIdSmall(Integer organizationId);
    Stage getStageById(Integer stageId);
    List<Stage> getStagesByProductId(Integer productId);

    // Create
    Stage createStage(CreateStageDTO stageDTO);

    // Update
    Stage updateStage(UpdateStageDTO stageDTO);

    // Delete
    void deleteStage(Integer stageId);
}

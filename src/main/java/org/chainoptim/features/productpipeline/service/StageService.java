package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.StagesSearchDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface StageService {

    // Fetch
    List<StagesSearchDTO> getStagesByOrganizationIdSmall(Integer organizationId);
    PaginatedResults<StagesSearchDTO> getStagesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    Stage getStageById(Integer stageId);
    List<Stage> getStagesByProductId(Integer productId);

    // Create
    Stage createStage(CreateStageDTO stageDTO);

    // Update
    Stage updateStage(UpdateStageDTO stageDTO);

    // Delete
    void deleteStage(Integer stageId);
}

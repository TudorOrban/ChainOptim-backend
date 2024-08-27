package org.chainoptim.features.goods.stage.service;

import org.chainoptim.features.goods.stage.dto.StagesSearchDTO;
import org.chainoptim.features.goods.stage.model.Stage;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface StageService {

    // Fetch
    List<StagesSearchDTO> getStagesByOrganizationIdSmall(Integer organizationId);
    PaginatedResults<StagesSearchDTO> getStagesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    Stage getStageById(Integer stageId);
    List<Stage> getStagesByProductId(Integer productId);
}

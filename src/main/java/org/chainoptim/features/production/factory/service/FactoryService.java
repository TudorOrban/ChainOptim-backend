package org.chainoptim.features.production.factory.service;

import org.chainoptim.features.production.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.production.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.production.factory.dto.FactoryOverviewDTO;
import org.chainoptim.features.production.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.production.factory.model.Factory;
import org.chainoptim.features.production.stageconnection.model.FactoryStageConnection;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface FactoryService {
    // Fetch
    List<FactoriesSearchDTO> getFactoriesByOrganizationIdSmall(Integer organizationId);
    List<Factory> getFactoriesByOrganizationId(Integer organizationId);
    PaginatedResults<FactoriesSearchDTO> getFactoriesByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage);
    Factory getFactoryById(Integer factoryId);
    Factory getFactoryWithStagesById(Integer factoryId);
    List<FactoryStageConnection> getFactoryStageConnectionsByFactoryId(Integer factoryId);
    FactoryOverviewDTO getFactoryOverviewById(Integer factoryId);

    // Create
    Factory createFactory(CreateFactoryDTO factoryDTO);

    // Update
    Factory updateFactory(UpdateFactoryDTO updateFactoryDTO);

    // Delete
    void deleteFactory(Integer factoryId);
}

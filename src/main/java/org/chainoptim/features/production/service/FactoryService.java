package org.chainoptim.features.production.service;

import org.chainoptim.features.production.dto.CreateFactoryDTO;
import org.chainoptim.features.production.dto.FactoriesSearchDTO;
import org.chainoptim.features.production.dto.FactoryOverviewDTO;
import org.chainoptim.features.production.dto.UpdateFactoryDTO;
import org.chainoptim.features.production.model.Factory;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
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

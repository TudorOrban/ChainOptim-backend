package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.evaluation.production.connection.model.FactoryStageConnection;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;
import java.util.Optional;

public interface FactoryService {
    // Fetch
    List<Factory> getFactoriesByOrganizationId(Integer organizationId);
    PaginatedResults<FactoriesSearchDTO> getFactoriesByOrganizationIdAdvanced(
            Integer organizationId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage);
    Optional<Factory> getFactoryById(Integer factoryId);
    Factory getFactoryWithStagesById(Integer factoryId);
    List<FactoryStageConnection> getFactoryStageConnectionsByFactoryId(Integer factoryId);

    // Create
    Factory createFactory(CreateFactoryDTO factoryDTO);

    // Update
    Factory updateFactory(UpdateFactoryDTO updateFactoryDTO);

    // Delete
    void deleteFactory(Integer factoryId);
}

package org.chainoptim.features.production.stage.service;

import org.chainoptim.features.production.stage.dto.CreateFactoryStageDTO;
import org.chainoptim.features.production.stage.dto.FactoryStageSearchDTO;
import org.chainoptim.features.production.stage.dto.UpdateFactoryStageDTO;
import org.chainoptim.features.production.stage.model.FactoryStage;

import java.util.List;

public interface FactoryStageService {

    // Fetch
    FactoryStage getFactoryStageById(Integer factoryStageId);
    List<FactoryStageSearchDTO> getFactoryStagesByFactoryId(Integer factoryId);
    List<FactoryStageSearchDTO> getFactoryStagesByOrganizationId(Integer organizationId);

    // Create
    FactoryStage createFactoryStage(CreateFactoryStageDTO factoryDTO, Boolean refreshGraph);

    // Update
    FactoryStage updateFactoryStage(UpdateFactoryStageDTO factoryDTO, Boolean refreshGraph);

    // Delete
    void deleteFactoryStage(Integer factoryStageId, Boolean refreshGraph);
}

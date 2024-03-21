package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.dto.CreateFactoryStageDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryStageDTO;
import org.chainoptim.features.factory.model.FactoryStage;

public interface FactoryStageService {

    // Fetch
    FactoryStage getFactoryStageById(Integer factoryStageId);

    // Create
    FactoryStage createFactoryStage(CreateFactoryStageDTO factoryDTO, Boolean refreshGraph);

    // Update
    FactoryStage updateFactoryStage(UpdateFactoryStageDTO factoryDTO);

    // Delete
    void deleteFactoryStage(Integer factoryStageId);
}

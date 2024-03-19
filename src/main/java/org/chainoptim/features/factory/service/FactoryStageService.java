package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.dto.CreateFactoryStageDTO;
import org.chainoptim.features.factory.model.FactoryStage;

public interface FactoryStageService {

    FactoryStage createFactoryStage(CreateFactoryStageDTO factoryDTO);
}

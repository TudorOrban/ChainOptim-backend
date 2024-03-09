package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.model.Stage;

import java.util.Optional;

public interface StageService {

    Stage getStageById(Integer stageId);
}

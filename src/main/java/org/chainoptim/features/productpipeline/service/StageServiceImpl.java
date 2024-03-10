package org.chainoptim.features.productpipeline.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StageServiceImpl implements StageService {

    private final StageRepository stageRepository;

    @Autowired
    public StageServiceImpl(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @Override
    public Stage getStageById(Integer stageId) {
        return stageRepository.findById(stageId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage with ID: " + stageId + " not found."));
    }
}

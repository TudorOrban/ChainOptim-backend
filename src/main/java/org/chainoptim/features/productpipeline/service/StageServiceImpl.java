package org.chainoptim.features.productpipeline.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.StageDTOMapper;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageServiceImpl implements StageService {

    private final StageRepository stageRepository;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public StageServiceImpl(StageRepository stageRepository, EntitySanitizerService entitySanitizerService) {
        this.stageRepository = stageRepository;
        this.entitySanitizerService = entitySanitizerService;
    }

    public Stage getStageById(Integer stageId) {
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage with ID: " + stageId + " not found."));
        Hibernate.initialize(stage.getStageInputs()); // Trigger lazy loading
        Hibernate.initialize(stage.getStageOutputs());

        return stage;
    }

    public List<Stage> getStagesByProductId(Integer stageId) {
        List<Stage> productStages = stageRepository.findByProductId(stageId);
        productStages.forEach(stage -> { // Trigger lazy loading
            Hibernate.initialize(stage.getStageInputs());
            Hibernate.initialize(stage.getStageOutputs());
        });
        return productStages;
    }

    public Stage createStage(CreateStageDTO stageDTO) {
        CreateStageDTO sanitizedStageDTO = entitySanitizerService.sanitizeCreateStageDTO(stageDTO);
        return stageRepository.save(StageDTOMapper.convertCreateStageDTOToProduct(sanitizedStageDTO));
    }

    public Stage updateStage(UpdateStageDTO stageDTO) {
        UpdateStageDTO sanitizedStageDTO = entitySanitizerService.sanitizeUpdateStageDTO(stageDTO);
        Stage stage = stageRepository.findById(sanitizedStageDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Stage with ID: " + sanitizedStageDTO.getId() + " not found."));

        stage.setName(sanitizedStageDTO.getName());
        stage.setDescription(sanitizedStageDTO.getDescription());

        stageRepository.save(stage);
        return stage;
    }

    public void deleteStage(Integer stageId) {
        Stage stage = new Stage();
        stage.setId(stageId);
        stageRepository.delete(stage);
    }
}

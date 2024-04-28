package org.chainoptim.features.productpipeline.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.StageDTOMapper;
import org.chainoptim.features.productpipeline.dto.StagesSearchDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageServiceImpl implements StageService {

    private final StageRepository stageRepository;
    private final EntitySanitizerService entitySanitizerService;
    private final SubscriptionPlanLimiterService planLimiterService;

    @Autowired
    public StageServiceImpl(StageRepository stageRepository,
                            SubscriptionPlanLimiterService planLimiterService,
                            EntitySanitizerService entitySanitizerService) {
        this.stageRepository = stageRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public List<StagesSearchDTO> getStagesByOrganizationIdSmall(Integer organizationId) {
        return stageRepository.findByOrganizationIdSmall(organizationId);
    }

    public PaginatedResults<StagesSearchDTO> getStagesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Stage> paginatedResults = stageRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
                paginatedResults.results.stream()
                        .map(StageDTOMapper::convertStageToStagesSearchDTO)
                        .toList(),
                paginatedResults.totalCount
        );
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
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(stageDTO.getOrganizationId(), Feature.FACTORY_STAGE, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Factory Stages for the current Subscription Plan.");
        }

        // Sanitize input
        CreateStageDTO sanitizedStageDTO = entitySanitizerService.sanitizeCreateStageDTO(stageDTO);

        return stageRepository.save(StageDTOMapper.convertCreateStageDTOToStage(sanitizedStageDTO));
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

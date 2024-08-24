package org.chainoptim.features.productpipeline.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.productpipeline.dto.StageDTOMapper;
import org.chainoptim.features.productpipeline.dto.StagesSearchDTO;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.repository.StageRepository;
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
}

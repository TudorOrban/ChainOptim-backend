package org.chainoptim.features.goods.stage.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.goods.stage.dto.CreateStageDTO;
import org.chainoptim.features.goods.stage.dto.StageDTOMapper;
import org.chainoptim.features.goods.stage.dto.UpdateStageDTO;
import org.chainoptim.features.goods.stage.model.Stage;
import org.chainoptim.features.goods.stage.repository.StageRepository;
import org.chainoptim.features.scanalysis.production.productgraph.service.ProductProductionGraphService;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StageWriteServiceImpl implements StageWriteService {

    private final StageRepository stageRepository;
    private final ProductProductionGraphService productionGraphService;
    private final EntitySanitizerService entitySanitizerService;
    private final SubscriptionPlanLimiterService planLimiterService;

    @Autowired
    public StageWriteServiceImpl(StageRepository stageRepository,
                                 ProductProductionGraphService productionGraphService,
                                 SubscriptionPlanLimiterService planLimiterService,
                                 EntitySanitizerService entitySanitizerService) {
        this.stageRepository = stageRepository;
        this.productionGraphService = productionGraphService;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public Stage createStage(CreateStageDTO stageDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(stageDTO.getOrganizationId(), Feature.PRODUCT_STAGE, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Product Stages for the current Subscription Plan.");
        }

        // Sanitize input
        CreateStageDTO sanitizedStageDTO = entitySanitizerService.sanitizeCreateStageDTO(stageDTO);

        Stage savedStage = stageRepository.save(StageDTOMapper.mapCreateStageDTOToStage(sanitizedStageDTO));

        productionGraphService.updateProductGraph(stageDTO.getProductId());

        return savedStage;
    }

    public Stage updateStage(UpdateStageDTO stageDTO) {
        UpdateStageDTO sanitizedStageDTO = entitySanitizerService.sanitizeUpdateStageDTO(stageDTO);
        Stage stage = stageRepository.findById(sanitizedStageDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Stage with ID: " + sanitizedStageDTO.getId() + " not found."));

        stage.setName(sanitizedStageDTO.getName());
        stage.setDescription(sanitizedStageDTO.getDescription());

        Stage savedStage = stageRepository.save(stage);

        productionGraphService.updateProductGraph(stage.getProductId());

        return savedStage;
    }

    public void deleteStage(Integer stageId) {
        Stage stage = new Stage();
        stage.setId(stageId);
        stageRepository.delete(stage);

        productionGraphService.updateProductGraph(stage.getProductId());
    }
}

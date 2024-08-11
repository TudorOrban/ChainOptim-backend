package org.chainoptim.features.factory.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.factory.dto.CreateFactoryStageDTO;
import org.chainoptim.features.factory.dto.FactoryDTOMapper;
import org.chainoptim.features.factory.dto.UpdateFactoryStageDTO;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.factory.repository.FactoryStageRepository;

import org.chainoptim.features.scanalysis.production.factorygraph.service.FactoryProductionGraphService;
import org.chainoptim.shared.enums.Feature;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoryStageServiceImpl implements FactoryStageService {

    private final FactoryStageRepository factoryStageRepository;
    private final FactoryProductionGraphService graphService;
    private final SubscriptionPlanLimiterService planLimiterService;

    @Autowired
    public FactoryStageServiceImpl(FactoryStageRepository factoryStageRepository,
                                   FactoryProductionGraphService graphService,
                                   SubscriptionPlanLimiterService planLimiterService) {
        this.factoryStageRepository = factoryStageRepository;
        this.graphService = graphService;
        this.planLimiterService = planLimiterService;
    }

    // Fetch
    public FactoryStage getFactoryStageById(Integer factoryStageId) {
        return factoryStageRepository.findByIdWithStage(factoryStageId)
                .orElseThrow(() -> new ResourceNotFoundException("Factory Stage with ID: " + factoryStageId + " not found."));
    }

    // Create
    @Transactional
    public FactoryStage createFactoryStage(CreateFactoryStageDTO stageDTO, Boolean refreshGraph) {
        // Check if plan limit is reached
//        if (planLimiterService.isLimitReached(stageDTO.getFactoryId(), Feature.FACTORY_STAGE, 1)) {
//            throw new PlanLimitReachedException("You have reached the limit of allowed factory stages for the current Subscription Plan.");
//        }

        FactoryStage factoryStage = factoryStageRepository.save(FactoryDTOMapper.convertCreateFactoryStageDTOToFactoryStage(stageDTO));

        // TODO: Fix this (the stageInputs and stageOutputs are null for some reason)
        if (Boolean.TRUE.equals(refreshGraph)) {
            graphService.updateFactoryGraph(factoryStage.getFactory().getId());
        }

        return factoryStage;
    }

    // Update
    public FactoryStage updateFactoryStage(UpdateFactoryStageDTO factoryDTO, Boolean refreshGraph) {
        FactoryStage factoryStage = factoryStageRepository.findById(factoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Factory Stage with ID: " + factoryDTO.getId() + " not found."));

        FactoryDTOMapper.updateFactoryStageWithUpdateFactoryStageDTO(factoryStage, factoryDTO);

        if (Boolean.TRUE.equals(refreshGraph)) {
            graphService.updateFactoryGraph(factoryStage.getFactory().getId());
        }

        return factoryStageRepository.save(factoryStage);
    }

    // Delete
    public void deleteFactoryStage(Integer factoryStageId, Boolean refreshGraph) {
        factoryStageRepository.deleteById(factoryStageId);

        if (Boolean.TRUE.equals(refreshGraph)) {
            graphService.updateFactoryGraph(factoryStageId);
        }
    }
}

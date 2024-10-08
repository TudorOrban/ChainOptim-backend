package org.chainoptim.features.production.stage.service;

import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.production.stage.dto.CreateFactoryStageDTO;
import org.chainoptim.features.production.factory.dto.FactoryDTOMapper;
import org.chainoptim.features.production.stage.dto.FactoryStageSearchDTO;
import org.chainoptim.features.production.stage.dto.UpdateFactoryStageDTO;
import org.chainoptim.features.production.stage.model.FactoryStage;
import org.chainoptim.features.production.stage.repository.FactoryStageRepository;

import org.chainoptim.features.production.factorygraph.service.FactoryProductionGraphService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<FactoryStageSearchDTO> getFactoryStagesByFactoryId(Integer factoryId) {
        return factoryStageRepository.findByFactoryId(factoryId);
    }

    public List<FactoryStageSearchDTO> getFactoryStagesByOrganizationId(Integer organizationId) {
        return factoryStageRepository.findByOrganizationId(organizationId);
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
        FactoryStage factoryStage = factoryStageRepository.findById(factoryStageId)
                .orElseThrow(() -> new ResourceNotFoundException("Factory Stage with ID: " + factoryStageId + " not found."));
        factoryStageRepository.deleteById(factoryStageId);

        if (Boolean.TRUE.equals(refreshGraph)) {
            graphService.updateFactoryGraph(factoryStage.getFactory().getId());
        }
    }
}

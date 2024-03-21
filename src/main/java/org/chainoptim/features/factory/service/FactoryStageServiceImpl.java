package org.chainoptim.features.factory.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.factory.dto.CreateFactoryStageDTO;
import org.chainoptim.features.factory.dto.FactoryDTOMapper;
import org.chainoptim.features.factory.dto.UpdateFactoryStageDTO;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.factory.repository.FactoryStageRepository;

import org.chainoptim.features.scanalysis.production.factorygraph.service.FactoryProductionGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoryStageServiceImpl implements FactoryStageService {

    private final FactoryStageRepository factoryStageRepository;
    private final FactoryProductionGraphService graphService;

    @Autowired
    public FactoryStageServiceImpl(FactoryStageRepository factoryStageRepository,
                                   FactoryProductionGraphService graphService) {
        this.factoryStageRepository = factoryStageRepository;
        this.graphService = graphService;
    }

    // Fetch
    public FactoryStage getFactoryStageById(Integer factoryStageId) {
        return factoryStageRepository.findByIdWithStage(factoryStageId)
                .orElseThrow(() -> new ResourceNotFoundException("Factory Stage with ID: " + factoryStageId + " not found."));
    }

    // Create
    public FactoryStage createFactoryStage(CreateFactoryStageDTO stageDTO, Boolean refreshGraph) {
        FactoryStage factoryStage = factoryStageRepository.save(FactoryDTOMapper.convertCreateFactoryStageDTOToFactoryStage(stageDTO));

        // TODO: Fix this (the stageInputs and stageOutputs are null for some reason)
//        if (Boolean.TRUE.equals(refreshGraph)) {
//            graphService.updateFactoryGraph(factoryStage.getFactory().getId());
//        }

        return factoryStage;
    }

    // Update
    public FactoryStage updateFactoryStage(UpdateFactoryStageDTO factoryDTO) {
        FactoryStage factoryStage = factoryStageRepository.findById(factoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Factory Stage with ID: " + factoryDTO.getId() + " not found."));

        FactoryDTOMapper.updateFactoryStageWithUpdateFactoryStageDTO(factoryStage, factoryDTO);

        return factoryStageRepository.save(factoryStage);
    }

    // Delete
    public void deleteFactoryStage(Integer factoryStageId) {
        factoryStageRepository.deleteById(factoryStageId);
    }
}

package org.chainoptim.features.factory.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.chainoptim.features.factory.dto.CreateFactoryStageDTO;
import org.chainoptim.features.factory.dto.FactoryDTOMapper;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.factory.repository.FactoryStageRepository;

import org.chainoptim.features.scanalysis.production.graph.service.FactoryProductionGraphService;
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

    public FactoryStage createFactoryStage(CreateFactoryStageDTO stageDTO, Boolean refreshGraph) {
        FactoryStage factoryStage = factoryStageRepository.save(FactoryDTOMapper.convertCreateFactoryStageDTOToFactoryStage(stageDTO));

//        if (Boolean.TRUE.equals(refreshGraph)) {
//            graphService.updateFactoryGraph(factoryStage.getFactory().getId());
//        }

        return factoryStage;
    }
}

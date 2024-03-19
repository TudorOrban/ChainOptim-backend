package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.dto.CreateFactoryStageDTO;
import org.chainoptim.features.factory.dto.FactoryDTOMapper;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.factory.repository.FactoryStageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoryStageServiceImpl implements FactoryStageService {

    private final FactoryStageRepository factoryStageRepository;

    @Autowired
    public FactoryStageServiceImpl(FactoryStageRepository factoryStageRepository) {
        this.factoryStageRepository = factoryStageRepository;
    }

    public FactoryStage createFactoryStage(CreateFactoryStageDTO stageDTO) {
        return factoryStageRepository.save(FactoryDTOMapper.convertCreateFactoryStageDTOToFactoryStage(stageDTO));
    }
}

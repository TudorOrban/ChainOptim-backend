package org.chainoptim.features.production.stageconnection.service;

import org.chainoptim.features.production.stageconnection.model.FactoryStageConnection;
import org.chainoptim.features.production.stageconnection.repository.FactoryStageConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryStageConnectionServiceImpl implements FactoryStageConnectionService {

    private final FactoryStageConnectionRepository factoryStageConnectionRepository;

    @Autowired
    public FactoryStageConnectionServiceImpl(
            FactoryStageConnectionRepository factoryStageConnectionRepository) {
        this.factoryStageConnectionRepository = factoryStageConnectionRepository;
    }

    public List<FactoryStageConnection> getConnectionsByFactoryId(Integer factoryId) {
        return factoryStageConnectionRepository.findFactoryStageConnectionsByFactoryId(factoryId);
    }
}

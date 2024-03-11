package org.chainoptim.features.evaluation.production.connection.service;

import org.chainoptim.features.evaluation.production.connection.model.FactoryStageConnection;
import org.chainoptim.features.evaluation.production.connection.repository.FactoryStageConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryStageConnectionService {

    private final FactoryStageConnectionRepository factoryStageConnectionRepository;

    @Autowired
    public FactoryStageConnectionService(FactoryStageConnectionRepository factoryStageConnectionRepository) {
        this.factoryStageConnectionRepository = factoryStageConnectionRepository;
    }

    public List<FactoryStageConnection> getConnectionsByFactoryId(Integer factoryId) {
        return factoryStageConnectionRepository.findFactoryStageConnectionsByFactoryId(factoryId);
    }
}

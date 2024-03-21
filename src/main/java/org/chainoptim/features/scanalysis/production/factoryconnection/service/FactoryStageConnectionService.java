package org.chainoptim.features.scanalysis.production.factoryconnection.service;

import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.scanalysis.production.factoryconnection.repository.FactoryStageConnectionRepository;
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

package org.chainoptim.features.scanalysis.production.factoryconnection.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.ConnectionDTOMapper;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.scanalysis.production.factoryconnection.repository.FactoryStageConnectionRepository;
import org.chainoptim.features.scanalysis.production.factorygraph.service.FactoryProductionGraphService;
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

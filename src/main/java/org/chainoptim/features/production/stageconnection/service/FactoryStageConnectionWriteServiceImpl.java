package org.chainoptim.features.production.stageconnection.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.production.stageconnection.dto.ConnectionDTOMapper;
import org.chainoptim.features.production.stageconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.production.stageconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.production.stageconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.production.stageconnection.repository.FactoryStageConnectionRepository;
import org.chainoptim.features.production.stageconnection.model.FactoryStageConnection;
import org.chainoptim.features.production.factorygraph.service.FactoryProductionGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoryStageConnectionWriteServiceImpl implements FactoryStageConnectionWriteService {

    private final FactoryStageConnectionRepository factoryStageConnectionRepository;
    private final FactoryProductionGraphService factoryProductionGraphService;

    @Autowired
    public FactoryStageConnectionWriteServiceImpl(
            FactoryStageConnectionRepository factoryStageConnectionRepository,
            FactoryProductionGraphService factoryProductionGraphService) {
        this.factoryStageConnectionRepository = factoryStageConnectionRepository;
        this.factoryProductionGraphService = factoryProductionGraphService;
    }

    public FactoryStageConnection createConnection(CreateConnectionDTO connectionDTO) {
        FactoryStageConnection connection = ConnectionDTOMapper.mapCreateConnectionDTOToFactoryStageConnection(connectionDTO);
        FactoryStageConnection savedConnection = factoryStageConnectionRepository.save(connection);
        factoryProductionGraphService.updateFactoryGraph(connectionDTO.getFactoryId());
        return savedConnection;
    }

    public FactoryStageConnection updateConnection(Integer connectionId, UpdateConnectionDTO connectionDTO) {
        FactoryStageConnection connection = factoryStageConnectionRepository.findById(connectionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Factory Connection with ID: " + connectionId + " not found."));
        ConnectionDTOMapper.setUpdateConnectionDTOTOFacotryStageConnection(connectionDTO, connection);
        FactoryStageConnection savedConnection = factoryStageConnectionRepository.save(connection);
        factoryProductionGraphService.updateFactoryGraph(connection.getFactoryId());
        return savedConnection;
    }

    public void deleteConnection(Integer connectionId) {
        factoryStageConnectionRepository.deleteById(connectionId);
    }

    public void findAndDeleteConnection(DeleteConnectionDTO connectionDTO) {
        FactoryStageConnection connection = factoryStageConnectionRepository.findConnectionByStageInputAndOutputIds(connectionDTO.getFactoryId(), connectionDTO.getSrcStageOutputId(), connectionDTO.getDestStageInputId())
                        .orElseThrow(() -> new ResourceNotFoundException("Factory Connection with Factory ID: " + connectionDTO.getFactoryId() + " and Stage Input ID: " + connectionDTO.getDestStageInputId() + " and Stage Output ID: " + connectionDTO.getSrcStageOutputId() + " not found."));
        factoryStageConnectionRepository.delete(connection);

        factoryProductionGraphService.updateFactoryGraph(connectionDTO.getFactoryId());
    }
}

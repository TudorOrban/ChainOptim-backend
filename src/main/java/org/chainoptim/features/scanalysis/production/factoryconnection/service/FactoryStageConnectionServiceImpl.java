package org.chainoptim.features.scanalysis.production.factoryconnection.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.ConnectionDTOMapper;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.scanalysis.production.factoryconnection.repository.FactoryStageConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryStageConnectionServiceImpl implements FactoryStageConnectionService {

    private final FactoryStageConnectionRepository factoryStageConnectionRepository;

    @Autowired
    public FactoryStageConnectionServiceImpl(FactoryStageConnectionRepository factoryStageConnectionRepository) {
        this.factoryStageConnectionRepository = factoryStageConnectionRepository;
    }

    public List<FactoryStageConnection> getConnectionsByFactoryId(Integer factoryId) {
        return factoryStageConnectionRepository.findFactoryStageConnectionsByFactoryId(factoryId);
    }

    public FactoryStageConnection createConnection(CreateConnectionDTO connectionDTO) {
        FactoryStageConnection connection = ConnectionDTOMapper.mapCreateConnectionDTOToFactoryStageConnection(connectionDTO);
        return factoryStageConnectionRepository.save(connection);
    }

    public FactoryStageConnection updateConnection(Integer connectionId, UpdateConnectionDTO connectionDTO) {
        FactoryStageConnection connection = factoryStageConnectionRepository.findById(connectionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Factory Connection with ID: " + connectionId + " not found."));
        ConnectionDTOMapper.setUpdateConnectionDTOTOFacotryStageConnection(connectionDTO, connection);
        return factoryStageConnectionRepository.save(connection);
    }

    public void deleteConnection(Integer connectionId) {
        factoryStageConnectionRepository.deleteById(connectionId);
    }

    public void findAndDeleteConnection(DeleteConnectionDTO connectionDTO) {
        FactoryStageConnection connection = factoryStageConnectionRepository.findConnectionByStageInputAndOutputIds(connectionDTO.getFactoryId(), connectionDTO.getOutgoingStageInputId(), connectionDTO.getIncomingStageOutputId())
                        .orElseThrow(() -> new ResourceNotFoundException("Factory Connection with Factory ID: " + connectionDTO.getFactoryId() + " and Stage Input ID: " + connectionDTO.getOutgoingStageInputId() + " and Stage Output ID: " + connectionDTO.getIncomingStageOutputId() + " not found."));
        factoryStageConnectionRepository.delete(connection);
    }
}

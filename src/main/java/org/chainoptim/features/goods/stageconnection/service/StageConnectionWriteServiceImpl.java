package org.chainoptim.features.goods.stageconnection.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.goods.stageconnection.dto.ConnectionDTOMapper;
import org.chainoptim.features.goods.stageconnection.dto.CreateConnectionDTO;
import org.chainoptim.features.goods.stageconnection.dto.DeleteConnectionDTO;
import org.chainoptim.features.goods.stageconnection.dto.UpdateConnectionDTO;
import org.chainoptim.features.goods.stageconnection.model.ProductStageConnection;
import org.chainoptim.features.goods.stageconnection.repository.StageConnectionRepository;
import org.chainoptim.features.goods.productgraph.service.ProductProductionGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StageConnectionWriteServiceImpl implements StageConnectionWriteService {

    private final StageConnectionRepository stageConnectionRepository;
    private final ProductProductionGraphService productionGraphService;

    @Autowired
    public StageConnectionWriteServiceImpl(
            StageConnectionRepository stageConnectionRepository,
            ProductProductionGraphService productionGraphService) {
        this.stageConnectionRepository = stageConnectionRepository;
        this.productionGraphService = productionGraphService;
    }

    public ProductStageConnection createConnection(CreateConnectionDTO connectionDTO) {
        ProductStageConnection connection = ConnectionDTOMapper.mapCreateConnectionDTOToStageConnection(connectionDTO);
        ProductStageConnection savedConnection = stageConnectionRepository.save(connection);
        productionGraphService.updateProductGraph(connectionDTO.getProductId());
        return savedConnection;
    }

    public ProductStageConnection updateConnection(Integer connectionId, UpdateConnectionDTO connectionDTO) {
        ProductStageConnection connection = stageConnectionRepository.findById(connectionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Factory Connection with ID: " + connectionId + " not found."));
        ConnectionDTOMapper.setUpdateConnectionDTOTOStageConnection(connectionDTO, connection);
        ProductStageConnection savedConnection = stageConnectionRepository.save(connection);
        productionGraphService.updateProductGraph(connection.getProductId());
        return savedConnection;
    }

    public void deleteConnection(Integer connectionId) {
        stageConnectionRepository.deleteById(connectionId);
    }

    public void findAndDeleteConnection(DeleteConnectionDTO connectionDTO) {
        ProductStageConnection connection = stageConnectionRepository.findConnectionByStageInputAndOutputIds(connectionDTO.getProductId(), connectionDTO.getSrcStageOutputId(), connectionDTO.getDestStageInputId())
                        .orElseThrow(() -> new ResourceNotFoundException("Factory Connection with Product ID: " + connectionDTO.getProductId() + " and Stage Input ID: " + connectionDTO.getDestStageInputId() + " and Stage Output ID: " + connectionDTO.getSrcStageOutputId() + " not found."));
        stageConnectionRepository.delete(connection);

        productionGraphService.updateProductGraph(connectionDTO.getProductId());
    }
}

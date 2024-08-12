package org.chainoptim.features.scanalysis.production.productconnection.dto;


import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;

public class ConnectionDTOMapper {

    private ConnectionDTOMapper() {}

    public static ProductStageConnection mapCreateConnectionDTOToStageConnection(CreateConnectionDTO createConnectionDTO) {
        ProductStageConnection stageConnection = new ProductStageConnection();
        stageConnection.setProductId(createConnectionDTO.getProductId());
        stageConnection.setSrcStageId(createConnectionDTO.getSrcStageId());
        stageConnection.setSrcStageOutputId(createConnectionDTO.getSrcStageOutputId());
        stageConnection.setDestStageId(createConnectionDTO.getDestStageId());
        stageConnection.setDestStageInputId(createConnectionDTO.getDestStageInputId());

        return stageConnection;
    }

    public static ProductStageConnection setUpdateConnectionDTOTOStageConnection(UpdateConnectionDTO updateConnectionDTO, ProductStageConnection stageConnection) {
        stageConnection.setSrcStageId(updateConnectionDTO.getSrcStageId());
        stageConnection.setSrcStageOutputId(updateConnectionDTO.getSrcStageOutputId());
        stageConnection.setDestStageId(updateConnectionDTO.getDestStageId());
        stageConnection.setDestStageInputId(updateConnectionDTO.getDestStageInputId());

        return stageConnection;
    }

}

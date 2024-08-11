package org.chainoptim.features.scanalysis.production.factoryconnection.dto;

import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;

public class ConnectionDTOMapper {

    private ConnectionDTOMapper() {}

    public static FactoryStageConnection mapCreateConnectionDTOToFactoryStageConnection(CreateConnectionDTO createConnectionDTO) {
        FactoryStageConnection factoryStageConnection = new FactoryStageConnection();
        factoryStageConnection.setFactoryId(createConnectionDTO.getFactoryId());
        factoryStageConnection.setSrcFactoryStageId(createConnectionDTO.getSrcFactoryStageId());
        factoryStageConnection.setSrcStageOutputId(createConnectionDTO.getSrcStageOutputId());
        factoryStageConnection.setDestFactoryStageId(createConnectionDTO.getDestFactoryStageId());
        factoryStageConnection.setDestStageInputId(createConnectionDTO.getDestStageInputId());

        return factoryStageConnection;
    }

    public static FactoryStageConnection setUpdateConnectionDTOTOFacotryStageConnection(UpdateConnectionDTO updateConnectionDTO, FactoryStageConnection factoryStageConnection) {
        factoryStageConnection.setFactoryId(updateConnectionDTO.getFactoryId());
        factoryStageConnection.setSrcFactoryStageId(updateConnectionDTO.getSrcFactoryStageId());
        factoryStageConnection.setSrcStageOutputId(updateConnectionDTO.getSrcStageOutputId());
        factoryStageConnection.setDestFactoryStageId(updateConnectionDTO.getDestFactoryStageId());
        factoryStageConnection.setDestStageInputId(updateConnectionDTO.getDestStageInputId());

        return factoryStageConnection;
    }

}

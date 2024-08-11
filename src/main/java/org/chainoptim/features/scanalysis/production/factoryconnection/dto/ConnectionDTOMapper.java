package org.chainoptim.features.scanalysis.production.factoryconnection.dto;

import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;

public class ConnectionDTOMapper {

    private ConnectionDTOMapper() {}

    public static FactoryStageConnection mapCreateConnectionDTOToFactoryStageConnection(CreateConnectionDTO createConnectionDTO) {
        FactoryStageConnection factoryStageConnection = new FactoryStageConnection();
        factoryStageConnection.setFactoryId(createConnectionDTO.getFactoryId());
        factoryStageConnection.setOutgoingFactoryStageId(createConnectionDTO.getOutgoingFactoryStageId());
        factoryStageConnection.setIncomingFactoryStageId(createConnectionDTO.getIncomingFactoryStageId());
        factoryStageConnection.setOutgoingStageInputId(createConnectionDTO.getOutgoingStageInputId());
        factoryStageConnection.setIncomingStageOutputId(createConnectionDTO.getIncomingStageOutputId());

        return factoryStageConnection;
    }

    public static FactoryStageConnection setUpdateConnectionDTOTOFacotryStageConnection(UpdateConnectionDTO updateConnectionDTO, FactoryStageConnection factoryStageConnection) {
        factoryStageConnection.setFactoryId(updateConnectionDTO.getFactoryId());
        factoryStageConnection.setOutgoingFactoryStageId(updateConnectionDTO.getOutgoingFactoryStageId());
        factoryStageConnection.setIncomingFactoryStageId(updateConnectionDTO.getIncomingFactoryStageId());
        factoryStageConnection.setOutgoingStageInputId(updateConnectionDTO.getOutgoingStageInputId());
        factoryStageConnection.setIncomingStageOutputId(updateConnectionDTO.getIncomingStageOutputId());

        return factoryStageConnection;
    }

}
